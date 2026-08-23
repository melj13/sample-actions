// Codility demo task: smallest positive integer (> 0) that does not occur in A.
// N in [1..100,000]; each element in [-1,000,000..1,000,000].
// O(N) time, O(N) space.
class Solution {
    public int solution(int[] A) {
        int n = A.length;
        // seen[v] tracks whether value v (1..n) appears in A.
        // The answer is always in 1..n+1, so larger values can be ignored.
        boolean[] seen = new boolean[n + 2];

        for (int value : A) {
            if (value > 0 && value <= n) {
                seen[value] = true;
            }
        }

        for (int candidate = 1; candidate <= n; candidate++) {
            if (!seen[candidate]) {
                return candidate;
            }
        }

        return n + 1;
    }
}
