package com.anish.controller;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Value("${jwt.verifier-key}")
    private String publicKey;

    @Value("${jwt.signing-key}")
    private String privateKey;

    @GetMapping("/get-key")
    public ResponseEntity<String> getKey()
    {
        return new ResponseEntity<>(publicKey, HttpStatus.OK);
    }

    /**
     * Returns the public Json Web Key (jwk) for the corresponding private key used for signing the oauth token
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @GetMapping("/get-jwk")
    public ResponseEntity<JSONObject> getJwsKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        JWK jwk = new RSAKey.Builder(getPublicKey())
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .algorithm(Algorithm.parse("RS256"))
                .build().toPublicJWK();
        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jwk.toJSONObject());
        response.put("keys", jsonArray);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private RSAPublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String publicKeyPem = publicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKeyPem = publicKeyPem.replace("-----END PUBLIC KEY-----", "");
        byte[] publicBytes = Base64.decodeBase64(publicKeyPem);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }
}
