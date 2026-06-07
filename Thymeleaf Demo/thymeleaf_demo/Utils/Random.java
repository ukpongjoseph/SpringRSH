import java.util.UUID;

public class Random {
    public static String genRandomString(int Lenght){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String token = uuid.substring(0, Math.min(uuid.length(), Lenght));
        return token;
    }
    public static void main(String[] args){
        String secret = genRandomString(32);
        System.out.println("Secret : " + secret);
    }
}
