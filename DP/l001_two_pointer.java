import java.util.* ; 
public class l001_two_pointer{

    //1.faith
    //2.recursive tree - memoization
    //3.recursive code
    //4.observation
    //5.tabulation
    //6.optimization

    public static void display(int[] arr){
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i] + " "); 
        }
        System.out.println(); 
    }

    public static void display2D(int[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static int fib_memo(int n,int[] dp){
        if(n<=1) {
        return dp[n] = n;
        }
        if(dp[n]!=0) return dp[n];

        int ans = fib_memo(n-1,dp) + fib_memo(n-2,dp) ;

        return dp[n] = ans;
    }

    public static int fib_tab(int N,int[] dp){
        for(int n=0;n<=N;n++){
        
        if(n<=1) {
            dp[n] = n;
            continue;
        }
        
        int ans = dp[n-1] + dp[n-2] ;
        dp[n] = ans;
    }

    return dp[N]; 

    }

    public static int fib_opti(int n){
        int a=0;
        int b=1; 
        for(int i=2;i<=n;i++){
            int sum = a+ b;
            a=b;
            b=sum; 

        }

        return b;
    }

    public static void fib(){
        int n=5;
        int[] dp=new int[n+1]; 
        
        System.out.println(fib_opti(n));

        // display(dp);
    }

    public static int mazePath_single_memo(int sr, int er, int sc,int ec , int[][] dp){ 
        if(sr==er && sc==ec){
            return dp[sr][sc]=1;
        }

        int ans=0;
        if(dp[sr][sc]!=0) return dp[sr][sc];

        if(sr+1<=er){
            ans+=mazePath_single_memo(sr+1,er,sc,ec,dp);
        }
        if(sc+1<=ec){
            ans+=mazePath_single_memo(sr,er,sc+1,ec,dp);
        }
        if(sc+1<=ec && sr+1<=er){
            ans+=mazePath_single_memo(sr+1,er,sc+1,ec,dp);
        }

        return dp[sr][sc] = ans;

    }

    public static void mazePath(){
        int sr=0,sc=0,er=3,ec=3;

        int[][] dp=new int[er][ec];
        System.out.println(mazePath_single_memo(sr,sc,er-1,ec-1,dp));

        display2D(dp);
    }

    public static void main(String[] args){
        // fib();
        mazePath();

    }
}