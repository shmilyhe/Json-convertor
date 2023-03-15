import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TestReader {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader("test.json"));
        BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(new FileInputStream("test.json"),"utf-8"));
        InputStreamReader ir =null;
    }
}
