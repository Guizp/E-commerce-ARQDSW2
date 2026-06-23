package edu.ifsp.loja.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SenhaUtil {
	private SenhaUtil() {}

	/**
	 * Gera o hash SHA-256 de uma senha em texto puro, retornando-o
	 * como uma string hexadecimal de 64 caracteres.
	 */
	public static String hash(String senhaTextoPuro) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] bytesHash = digest.digest(senhaTextoPuro.getBytes("UTF-8"));

			StringBuilder sb = new StringBuilder();
			for (byte b : bytesHash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					sb.append('0');
				}
				sb.append(hex);
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
			throw new RuntimeException("Erro ao gerar hash da senha", e);
		}
	}

	/**
	 * Compara uma senha em texto puro com um hash armazenado.
	 */
	public static boolean confere(String senhaTextoPuro, String hashArmazenado) {
		return hash(senhaTextoPuro).equals(hashArmazenado);
	}
}
