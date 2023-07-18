package id.ic.ms.audit;

import id.ic.ms.audit.configuration.EncryptConfig;

public class Console {

    // This class is used to generate encrypted value(s) for application.properties

    private static EncryptConfig config = new EncryptConfig();

    public static void main(String[] args) {
        String[] strArr = {"audit_user"};
        encrypt(strArr);
        //decrypt(strArr);
    }

    static void encrypt(String[] arr) {
        for (String str : arr) {
            String encrypted = config.stringEncryptor().encrypt(str);
            System.out.println("Clear : " + str);
            System.out.println("Encrypted : ENC(" + encrypted + ")");
            System.out.println();
        }
    }

    static void decrypt(String[] arr) {
        for (String str : arr) {
            String encrypted = config.stringEncryptor().decrypt(str);
            System.out.println("Encrypted : " + str);
            System.out.println("Clear :" + encrypted);
            System.out.println();
        }
    }
}
