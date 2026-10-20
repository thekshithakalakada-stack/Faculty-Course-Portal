package com.portal.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;

/**
 * =====================================================================
 * Security Utility: PasswordUtil
 * 
 * Provides industry-standard password hashing and verification using
 * PBKDF2 with HMAC-SHA256 and cryptographically secure salting.
 * Compatible with standard Java SE and Apache Tomcat 9 without external libraries.
 * =====================================================================
 */
public class PasswordUtil {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256; // bits
    private static final int SALT_BYTES = 16;  // 128 bits
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Hashes a plain text password using PBKDF2WithHmacSHA256 with a unique random salt.
     * Format: <iterations>:<saltBase64>:<hashBase64>
     * 
     * @param plainPassword plain text password
     * @return securely formatted password hash string
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);

        byte[] hash = pbkdf2(plainPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH);

        return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Verifies a plain text password against a stored password string.
     * Supports both PBKDF2 hashed strings and legacy plain-text fallback.
     * 
     * @param plainPassword plain text password entered by user
     * @param storedHash stored password or hash from database
     * @return true if credentials match, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String storedHash) {
        if (plainPassword == null || storedHash == null) {
            return false;
        }

        // Check if stored string is in PBKDF2 format (iterations:salt:hash)
        String[] parts = storedHash.split(":");
        if (parts.length == 3) {
            try {
                int iterations = Integer.parseInt(parts[0]);
                byte[] salt = Base64.getDecoder().decode(parts[1]);
                byte[] expectedHash = Base64.getDecoder().decode(parts[2]);

                byte[] actualHash = pbkdf2(plainPassword.toCharArray(), salt, iterations, expectedHash.length * 8);

                // Constant-time comparison to protect against timing attacks
                return MessageDigest.isEqual(expectedHash, actualHash);
            } catch (Exception e) {
                System.err.println("[PasswordUtil] Error parsing stored hash: " + e.getMessage());
                return false;
            }
        }

        // Backward compatibility fallback for legacy unhashed seeded accounts
        return storedHash.equals(plainPassword);
    }

    /**
     * Checks if the stored password string is not yet hashed with PBKDF2.
     * 
     * @param storedHash password string from database
     * @return true if stored string needs to be upgraded/rehashed
     */
    public static boolean needsRehash(String storedHash) {
        if (storedHash == null) {
            return false;
        }
        String[] parts = storedHash.split(":");
        if (parts.length != 3) {
            return true;
        }
        try {
            Integer.parseInt(parts[0]);
            Base64.getDecoder().decode(parts[1]);
            Base64.getDecoder().decode(parts[2]);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error computing PBKDF2 hash: " + e.getMessage(), e);
        }
    }
}
