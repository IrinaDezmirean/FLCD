public class Main
{
    public static void main(String[] args)
    {
        try
        {
            MyScanner scanner = new MyScanner();
            scanner.scan("p1.txt");
            scanner.scan("p2.txt");
            scanner.scan("p3.txt");
            scanner.scan("p3_error.txt");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}