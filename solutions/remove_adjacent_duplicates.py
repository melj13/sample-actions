from itertools import groupby


def remove_adjacent_duplicates(numbers):
    """Return a new list with runs of adjacent duplicates collapsed to one.

    Order is preserved and the input list is never modified. Only
    *adjacent* repeats are removed, so a value that reappears later in
    the list is kept:

        [1, 1, 2, 2, 2, 3, 4, 4, 4, 2, 5, 5] -> [1, 2, 3, 4, 2, 5]
    """
    result = []
    for number in numbers:
        if not result or number != result[-1]:
            result.append(number)
    return result


def remove_adjacent_duplicates_groupby(numbers):
    """Same behaviour, written with itertools.groupby."""
    return [key for key, _group in groupby(numbers)]
