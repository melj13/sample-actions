/*
 * Complete the 'oddNumbers' function below.
 *
 * The function is expected to return an INTEGER_ARRAY.
 * The function accepts following parameters:
 *  1. INTEGER l
 *  2. INTEGER r
 */
int* oddNumbers(int l, int r, int* result_count) {
    /* first odd value at or after l */
    int first = (l % 2 == 0) ? l + 1 : l;

    if (first > r) {
        *result_count = 0;
        return NULL;
    }

    int count = (r - first) / 2 + 1;
    int* result = malloc(count * sizeof(int));

    for (int i = 0; i < count; i++) {
        result[i] = first + 2 * i;
    }

    *result_count = count;
    return result;
}
