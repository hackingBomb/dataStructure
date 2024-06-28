package Recursive;

public class recursive {

    public static void main(String[] args) {

        // TODO : 인덱스 출력하기 1
        split(0, 7);
        System.out.println("-----------");

        // TODO : 인덱스 합 출력하기 2
        System.out.printf("인덱스의 합 : %d\n", split2(0, 7));
        System.out.println("-----------");

        // TODO : 배열의 값의 합 출력하기 3
        int[] arr = {4, 2, 5, 1, 5, 3, 1, 2};
        System.out.printf("배열의 합 : %d", sum(arr, 0, arr.length - 1));

    }

    public static int sum(int[] arr, int startIndex, int endIndex) {
        // TODO : 배열의 합 메서드
        if (startIndex == endIndex) {
            return arr[startIndex];
        }
        int middleIndex = (startIndex + endIndex) / 2;
        return sum(arr, startIndex, middleIndex) + sum(arr, middleIndex + 1, endIndex);
    }

    public static void split(int startIndex, int endIndex) {
        // TODO : 배열 인덱스 출력 메서드
        if (startIndex == endIndex) {
            System.out.println(startIndex);
            return;
        }
        int middleIndex = (startIndex + endIndex) / 2;
        split(startIndex, middleIndex);
        split(middleIndex + 1, endIndex);
    }

    public static int split2(int startIndex, int endIndex) {
        // TODO : 인덱스 합 메서드
        if (startIndex == endIndex) {
            return startIndex;
        }
        int middleIndex = (startIndex + endIndex) / 2;
        return split2(startIndex, middleIndex) + split2(middleIndex + 1, endIndex);
    }
}