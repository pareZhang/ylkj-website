package org.springblade.core.secure.utils;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

public class Sm4Util {

	public static final BouncyCastleProvider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();
	private static final String BOUNCY_CASTLE_PROVIDER_NME = BouncyCastleProvider.PROVIDER_NAME;
	private static final String ENCODING = "UTF-8";

	public static final String ALGORITHM_NAME = "SM4";

	// 加密算法/分组加密模式/分组填充方式
	// PKCS5Padding-以8个字节为一组进行分组加密
	// 定义分组加密模式使用：PKCS5Padding
	public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";

	// 128-32位16进制；256-64位16进制
	public static final int DEFAULT_KEY_SIZE = 128;

	static {
		if(Security.getProperty(BOUNCY_CASTLE_PROVIDER_NME)==null){
			Security.addProvider(BOUNCY_CASTLE_PROVIDER);
		}
	}


	/**
	 * 自动生成密钥
	 *
	 * @return
	 * @explain
	 */
	public static String generateKey() throws Exception {
		return new String(Hex.encodeHex(generateKey(DEFAULT_KEY_SIZE),false));
	}

	/**
	 * @param keySize
	 * @return
	 * @throws Exception
	 * @explain
	 */
	public static byte[] generateKey(int keySize) throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BOUNCY_CASTLE_PROVIDER_NME);
		kg.init(keySize, new SecureRandom());
		return kg.generateKey().getEncoded();
	}

	/**
	 * 生成ECB暗号
	 * @explain ECB模式（电子密码本模式：Electronic codebook）
	 * @param algorithmName
	 *            算法名称
	 * @param mode
	 *            模式
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithmName, BOUNCY_CASTLE_PROVIDER_NME);
		Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
		cipher.init(mode, sm4Key);
		return cipher;
	}

	/**
	 * sm4加密
	 * @explain 加密模式：ECB
	 *          密文长度不固定，会随着被加密字符串长度的变化而变化
	 * @param hexKey
	 *            16进制密钥（忽略大小写）
	 * @param paramStr
	 *            待加密字符串
	 * @return 返回16进制的加密字符串
	 * @throws Exception
	 */
	public static String encryptEcb(String hexKey, String paramStr) throws Exception {
		String cipherText = "";
		// 16进制字符串-->byte[]
		byte[] keyData = ByteUtils.fromHexString(hexKey);
		// String-->byte[]
		byte[] srcData = paramStr.getBytes(ENCODING);
		// 加密后的数组
		byte[] cipherArray = encrypt_Ecb_Padding(keyData, srcData);
		// byte[]-->hexString
		cipherText = ByteUtils.toHexString(cipherArray);
		return cipherText;
	}

	/**
	 * 加密模式之Ecb
	 * @explain
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt_Ecb_Padding(byte[] key, byte[] data) throws Exception {
		Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	/**
	 * sm4解密
	 * @explain 解密模式：采用ECB
	 * @param hexKey
	 *            16进制密钥
	 * @param cipherText
	 *            16进制的加密字符串（忽略大小写）
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decryptEcb(String hexKey, String cipherText) throws Exception {
		// 用于接收解密后的字符串
		String decryptStr = "";
		// hexString-->byte[]
		byte[] keyData = ByteUtils.fromHexString(hexKey);
		// hexString-->byte[]
		byte[] cipherData = ByteUtils.fromHexString(cipherText);
		// 解密
		byte[] srcData = decrypt_Ecb_Padding(keyData, cipherData);
		// byte[]-->String
		decryptStr = new String(srcData, ENCODING);
		return decryptStr;
	}

	/**
	 * 解密
	 * @explain
	 * @param key
	 * @param cipherText
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt_Ecb_Padding(byte[] key, byte[] cipherText) throws Exception {
		Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(cipherText);
	}


	/**
	 * 校验加密前后的字符串是否为同一数据
	 * @explain
	 * @param hexKey
	 *            16进制密钥（忽略大小写）
	 * @param cipherText
	 *            16进制加密后的字符串
	 * @param paramStr
	 *            加密前的字符串
	 * @return 是否为同一数据
	 * @throws Exception
	 */
	public static boolean verifyEcb(String hexKey, String cipherText, String paramStr) throws Exception {
		// 用于接收校验结果
		boolean flag = false;
		// hexString-->byte[]
		byte[] keyData = ByteUtils.fromHexString(hexKey);
		// 将16进制字符串转换成数组
		byte[] cipherData = ByteUtils.fromHexString(cipherText);
		// 解密
		byte[] decryptData = decrypt_Ecb_Padding(keyData, cipherData);
		// 将原字符串转换成byte[]
		byte[] srcData = paramStr.getBytes(ENCODING);
		// 判断2个数组是否一致
		flag = Arrays.equals(decryptData, srcData);
		return flag;
	}


	public static void exportTxt(HttpServletResponse response,String fileName,String text) throws IOException {
		response.setCharacterEncoding(Charsets.UTF_8.name());
		fileName = URLEncoder.encode(fileName, Charsets.UTF_8.name());
		response.setContentType("text/plain");
		response.addHeader("Content-Disposition","attachment;filename="+fileName+".crt");
		BufferedOutputStream buff = null;
		ServletOutputStream outStr = null;
		try {
			outStr = response.getOutputStream();
			buff = new BufferedOutputStream(outStr);
			buff.write(text.getBytes("UTF-8"));
			buff.flush();
			buff.close();
		}catch (Throwable var1){
			throw var1;
		}finally {
			buff.close();
			outStr.close();
		}
	}


	public static void main(String[] args) {
		try {
			String str = decryptEcb("b510c43a0722c537a20f5773f9efe826","1239aa87d8bcca05628c871b74e5a82d6cb3b6c5fabe1df48d71a89a2359a720943d9f3d39089bd8ffa4d1355913b51a1bcbcca39ef30d14db74ee0a1d610ab3e0d9e81c17ee11f977e87ecbf67f3b0cc3cd59b10b00484fb5198558b72fdaefe0d9e81c17ee11f977e87ecbf67f3b0c59320d3696d819be7471891c85c3a1794bcfcdadb207083a2a7521e692b6096cb71dcf28b117881c7c0848ea6b626667b1154205e0c5d81b8cf61e3fc8e54e0b57bd05140ed5fa58517db8b1c97f070b8c12a3b0c8525a121c68fa153378876e93d87680289bebfea5a67aa2335e827d9da0ed3cf88e4f19413f22f11af93a949a578352737fd4da4ed92da33b7e20752593ff29da54e220ed5c3be9587d6eac69b5f75db12fc86bc6698a06911ffe6a9d1846667bac42bee216825277cca8729a29dc1933d03e168155e5d07018f11fe93a2f5c41409fc22562b5d9b45ea99cb03c454edf9807602ec105f6d16a01333602702fe5b2bb1d412b95568301ec5215166d448d3b9150c8703ff44c715e662d9c45b4edf9936ddf0352d43bb7ca41b038b495733b1769e886f920f9111817d50070ec0f16a8253f77e1c2b6b08610bf9431f24ca3af9553620aae51317479873337e687168dacb79b3666f1ed6803f6415b93a63112669e1096b3262641c00adc2e9f1b8265b3c4d2b3e1729b348a851705a1d3af9c8469b80ef7848f79b21ca8a2371ce01e8f75499cad8d7af98d3f5159c4bab1c3255e63d5ce664408596dbe8d217555958eb1b80630cb610001248a4eb7e1bea91b46cc8ae11068e05672989b9ace859f2e65a3e03c4d501d060a906920ce105e6c4f3a7be0549136ff6406de39b779f4c9f7f1a6ab22842a4f2b98e0da7cec3598f198f69c8dceedd63274064656ec63e9f74bacb8396f311cc33c30cb5118a67e15c0c14c2d908b75d3085c601ef4f211910dbe019388211c551f47e8bf2a5fa847a54625e832a72443691eb9bced4a6f7d807760c4d4e841d5c93a7d6b4263245af43130cb4a38002ae5f2339ba93080192fd3d9d820ccd3605bd9ab05d53fdcbf8163c483f4226c68b32be2775faf8250d4a27ca1601cead94f1655d67310c7600e0bf4b17b3c0a8f762adc4380fed7dde18fb5e31d167c6fcd8e71535eb02c7226ba3985afee2293e053e5ac02b6f8918d673d739ad0bec1d7ed41cc16378186e50f22dc5b1e6d52e9ce50a85c0c022730aea3c92138d4dd543a4d015ef8bf3c07ee433498ab25dc7907f16976cd37b5c96636b5579b5ee3282aeffe0ae37a2814c3e05f272e792d39cf02a83b8c227af90e5ffe964ebebb35f2e61c859ba6f95802615f21a59c8e7b0f5f4dd208ef53be64491f75859c151812d68145ed9991489193334222c2d2f4bd3656fe8f1695796c5b51b7e841e3f080d2ae3d40a54b1e46e79fb00f50469da279256138a2b2ce00717f2314ad7d2a449c64cfcb4decb3756e17e43e6c8caeed11ebb819ecd3b56be9a1009058f852d821217091fb9ca1682476c7ad4406cf04116d039352e1cdf4e48fc4ad540f6fd2c6307c91a952bf64297609a08661214217c666fcf022ae5a635904abfc6ffe5f75d353084f7e6877613d6676dd");
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
