public class Main {
    public static void main(String[] args) {
        // int[] arr = new int[4];
        int arr[] = { 1, 2, 3, 4 };

        for (int i = 0; i < arr.length; i++)
            System.out.println(arr[i]);

        int mdarr[][] = new int[3][];
        mdarr[0] = new int[1];
        mdarr[1] = new int[2];
        mdarr[2] = new int[3];

        for (int i = 0; i < mdarr.length; i++) {
            for (int j = 0; j < mdarr[i].length; j++) {
                System.out.println(mdarr[i][j]);
            }
        }
    }
}
