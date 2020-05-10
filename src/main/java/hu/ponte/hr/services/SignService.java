package hu.ponte.hr.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Service
public class SignService {

    private PrivateKey privateKey;

    public SignService() {
        try {
            this.privateKey = loadPrivateKey();
        } catch (InvalidKeySpecException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String generateSignature(String imageId) {
        Signature sig = null;
        byte[] signatureBytes = new byte[0];
        try {
            sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(privateKey);
            sig.update(imageId.getBytes("UTF8"));
            signatureBytes = sig.sign();
        } catch (NoSuchAlgorithmException | SignatureException | UnsupportedEncodingException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return Arrays.toString(Base64.getEncoder().encode(signatureBytes));
    }

    private PrivateKey loadPrivateKey() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException {
        byte[] keyBytes = this.getClass().getClassLoader().getResourceAsStream("config/keys/key.private").readAllBytes();

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
