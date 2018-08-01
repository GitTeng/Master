package tv.huan.master.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class SecureUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SecureUtil.class);

	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	public static byte[] base64Decode(byte[] inputByte) throws IOException {
		return Base64.decodeBase64(inputByte);
	}

	public static byte[] base64Encode(byte[] inputByte) {
		return Base64.encodeBase64(inputByte);
	}

	public static byte[] sha1X16(String data, String encoding) {
		byte[] bytes = sha1(data, encoding);
		StringBuilder sha1StrBuff = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			if (Integer.toHexString(0xFF & bytes[i]).length() == 1) {
				sha1StrBuff.append("0").append(
						Integer.toHexString(0xFF & bytes[i]));
			} else
				sha1StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
		}
		try {
			return sha1StrBuff.toString().getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] sha1(String datas, String encoding) {
		try {
			return sha1(datas.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			logger.error("SHA1计算失败", e);
		}
		return null;
	}

	public static byte[] sha1(byte[] data) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(data);
			return md.digest();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SHA1计算失败", e);
		}
		return null;
	}

	public static byte[] signBySoft(PrivateKey privateKey, byte[] data)
			throws SecurityException {
		byte[] result = null;
		try {
			Signature st = Signature.getInstance("SHA1withRSA");
			st.initSign(privateKey);
			st.update(data);
			result = st.sign();
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e.getMessage());
		} catch (InvalidKeyException e) {
			throw new SecurityException(e.getMessage());
		} catch (SignatureException e) {
			throw new SecurityException(e.getMessage());
		}

		return result;
	}

	public static byte[] deflater(byte[] inputByte) throws SecurityException {
		int compressedDataLength = 0;
		Deflater compresser = new Deflater();
		compresser.setInput(inputByte);
		compresser.finish();
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.deflate(result);
				o.write(result, 0, compressedDataLength);
			}
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				throw new SecurityException(e.getMessage());
			}
		}
		compresser.end();
		return o.toByteArray();
	}

	public static byte[] inflater(byte[] inputByte) throws SecurityException {
		int compressedDataLength = 0;
		Inflater compresser = new Inflater(false);
		compresser.setInput(inputByte, 0, inputByte.length);
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.inflate(result);
				if (compressedDataLength == 0) {
					break;
				}
				o.write(result, 0, compressedDataLength);
			}
		} catch (Exception ex) {
			System.err.println("Data format error!\n");
			throw new SecurityException(ex.getMessage());
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				throw new SecurityException(e.getMessage());
			}
		}
		compresser.end();
		return o.toByteArray();
	}
}
