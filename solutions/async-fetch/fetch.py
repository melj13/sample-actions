import asyncio
import aiohttp


class FetchError(Exception):
    """Raised when a URL still fails after all retries."""


async def fetch_one(session, url, *, timeout, retries=1, backoff=0.5):
    """Fetch a single URL, retrying on transient failures.

    The timeout applies to each individual attempt, not to the whole
    retry sequence. Client errors (4xx) are not retried -- they will
    not succeed on a second attempt.
    """
    last_error = None

    for attempt in range(retries + 1):
        try:
            async with session.get(
                url, timeout=aiohttp.ClientTimeout(total=timeout)
            ) as response:
                if 400 <= response.status < 500:
                    raise FetchError(f"{url} returned {response.status}")
                response.raise_for_status()
                return await response.text()

        except asyncio.CancelledError:
            # Never swallow cancellation -- it is not a transient failure.
            raise
        except FetchError:
            raise
        except (aiohttp.ClientError, asyncio.TimeoutError) as exc:
            last_error = exc
            if attempt < retries:
                await asyncio.sleep(backoff * (2 ** attempt))

    raise FetchError(f"{url} failed after {retries + 1} attempts: {last_error!r}")


async def fetch_all(urls, *, timeout=5.0, retries=1):
    """Fetch every URL concurrently; one failure never cancels the rest.

    Returns a list of (url, result) pairs where result is the response
    body on success or the exception on failure.
    """
    async with aiohttp.ClientSession() as session:
        tasks = [
            fetch_one(session, url, timeout=timeout, retries=retries)
            for url in urls
        ]
        results = await asyncio.gather(*tasks, return_exceptions=True)

    return list(zip(urls, results))


async def main():
    urls = [
        "https://example.com/1",
        "https://example.com/2",
        "https://example.com/3",
        "https://example.com/4",
        "https://example.com/5",
    ]

    for url, result in await fetch_all(urls, timeout=2.0):
        if isinstance(result, Exception):
            print(f"FAILED  {url}: {result}")
        else:
            print(f"OK      {url}: {len(result)} bytes")


if __name__ == "__main__":
    asyncio.run(main())
