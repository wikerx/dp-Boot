package com.scott.dp.common.utils;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import javax.crypto.Cipher;
import org.apache.commons.lang.ArrayUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 总结：公钥和私钥是成对的，它们互相解密。
	公钥加密，私钥解密。发送方先自己私钥签名后用对方公钥加密发送，接收方先用私钥解密后用公钥验证签名。
	公钥用于对数据进行加密，私钥用于对数据进行解密。当然了，这个也可以很直观的理解：公钥就是公开的密钥，其公开了大家才能用它来加密数据。
	私钥是私有的密钥，谁有这个密钥才能够解密密文。否则大家都能看到私钥，就都能解密，那不就乱套了。
 * @author linxiaowei
 *
 */
public class RSATools {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 使用BASE64进行加密/解密：
	 * @param privateKey
	 * @return
	 */
	public static byte[] decryptBASE64(String privateKey) {
		byte[] output = null;
		try {
			output = (new BASE64Decoder()).decodeBuffer(privateKey);
			return output;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	public static String encryptBASE64(byte[] keyBytes) {
		String s = (new BASE64Encoder()).encode(keyBytes);
		return s;
	}

	/**
	 * 初始化密钥，生产一对公钥私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		
		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return encryptBASE64(key.getEncoded());
	}
	
	/**
	 * 加密<br>
	 * 用私钥加密
	 * @param data 要加密的参数
	 * @param privateKeys 密钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String data, String privateKeys) {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(privateKeys);
		System.out.println("****************RSA私钥加密，传进来内容*************:"+data);
		String rtnStr=StringUtils.EMPTY;
		//将参数先MD5先减少长度
		if(null!=data){
			byte[] dataByte=MD5Utils.MD5Encode(data).getBytes();
			System.out.println("*************先MD5Key加密后****************:"+MD5Utils.MD5Encode(data));
			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			byte[] enBytes = null;
			try {
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
				Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

				// 对数据加密
				Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);
				// 加密时超过117字节就报错。为此采用分段加密的办法来加密
				for (int i = 0; i < dataByte.length; i += 64) {
					// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
					byte[] doFinal = cipher.doFinal(ArrayUtils
							.subarray(dataByte, i, i + 64));
					enBytes = ArrayUtils.addAll(enBytes, doFinal);
				}
				// 转化为字符串
				rtnStr = (new BASE64Encoder()).encode(enBytes);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("**************RSA私钥匙加密异常*****************" +e);
	        }
			
			System.out.println("**************RSA私钥加密，RSA最终加密后密文*******************:"+rtnStr);
		}
		return rtnStr;
	}
	
	
	/**
	 * 解密<br>
	 * 用公钥解密
	 * @param data 要加密的参数
	 * @param publicKeys 公钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String data, String publicKeys){
		System.out.println("*********************RSA公匙解密，传进来内容：*************************"+data);	
		String rtnStr=StringUtils.EMPTY;
		if(data!=null){
			byte[] dataBytes = decryptBASE64(data);
			// 对密钥解密
			byte[] keyBytes = decryptBASE64(publicKeys);
			StringBuffer sb = new StringBuffer();
			try {
				// 取得公钥
				X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
				Key publicKey = keyFactory.generatePublic(x509KeySpec);

				// 对数据解密
				Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
				cipher.init(Cipher.DECRYPT_MODE, publicKey);
				//cipher.doFinal(dataBytes);

				// 解密时超过128字节就报错。为此采用分段解密的办法来解密,解析报错，暂时注释掉
				for (int i = 0; i < dataBytes.length; i += 128) {
					//这里循环再次解密会报错，先跳出
//					if(i>0){
//						continue;
//					}
					byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(dataBytes, i,i + 128));
					sb.append(new String(doFinal));
				}
				rtnStr=data=sb.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("**************RSA公匙解密异常*****************" +e);
	        }
			System.out.println("*********************RSA公匙最终解密后*********************："+rtnStr);	
		}
		return rtnStr;

	}
	
	
	
	/**
	 * 校验数字签名，使用公钥解密后在校验
	 * @param data 排序后的数据
	 * @param publicKey 公钥
	 * @param sign RSA加密后的数字签名
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 此方法不问题，偶尔会报错
	 */
	public static boolean checkDataByPublicKey(String publicKey,String data, String sign) {
		boolean flag=false;
		//用公钥解密
		String decodedData = null;
		System.out.println("***************传进来的公钥publicKey*********:"+publicKey);
		System.out.println("***************传进来排序后的字符串***********:"+data);
		System.out.println("***************传进来的密文sign**************:"+sign);
		try {
			if(StringUtils.isNotBlank(publicKey)){
				data=MD5Utils.MD5Encode(data);
				decodedData = RSATools.decryptByPublicKey(sign, publicKey);
				System.out.println("***************解密后字符******************: " + decodedData);
				if(StringUtils.equals(data, decodedData)){
					flag=true;
				}
			}else{
				System.err.println("***************传进来的公钥publicKey为空*********:");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("******************RSA公钥匙解密异常**********************: " +e);
		}
		return flag;
	}
	
	
	/**
	 * 加密<br>
	 * 用公钥加密
	 * @param data 要加密的参数
	 * @param publicKeys 密钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, String publicKeys) {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(publicKeys);
		String rtnStr=StringUtils.EMPTY;
		System.out.println("****************RSA公钥加密，传进来内容*************:"+data);
		//将参数先MD5先减少长度
		if(null!=data){
			//byte[] dataByte=MD5.MD5Encode(data).getBytes();
			byte[] dataByte=data.getBytes();
			//System.out.println("*************先MD5Key加密****************:"+MD5.MD5Encode(data));
			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			byte[] enBytes = null;
			try {
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
				Key publicKey = keyFactory.generatePublic(x509KeySpec);

				// 对数据加密
				Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				// 加密时超过117字节就报错。为此采用分段加密的办法来加密
				for (int i = 0; i < dataByte.length; i += 64) {
					// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
					byte[] doFinal = cipher.doFinal(ArrayUtils
							.subarray(dataByte, i, i + 64));
					enBytes = ArrayUtils.addAll(enBytes, doFinal);
				}
				// 转化为字符串
				rtnStr = (new BASE64Encoder()).encode(enBytes);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("**************RSA公钥加密异常*****************" +e);
	        }
			System.out.println("**************RSA公钥加密，最终加密后密文*******************:"+rtnStr);
		}
		return rtnStr;
	}
	

	
	/**
	 * 解密<br>
	 * 用私钥解密
	* @param data 要加密的参数
	 * @param privateKeys 公钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data, String privateKeys){
		System.out.println("*********************RSA私钥解密，传进来内容：*************************"+data);	
		String rtnStr=StringUtils.EMPTY;
		if(data!=null){
			try {
				byte[] dataBytes = decryptBASE64(data);
				// 对密钥解密
				byte[] keyBytes = decryptBASE64(privateKeys);
				
				StringBuffer sb = new StringBuffer();
				// 取得私钥
				PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
				Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
				Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				//cipher.doFinal(dataBytes);
				
				// 解密时超过128字节就报错。为此采用分段解密的办法来解密
				for (int i = 0; i < dataBytes.length; i += 128) {
					//这里循环再次解密会报错，先跳出
//					if(i>0){
//						continue;
//					}
					byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(dataBytes, i,
							i + 128));
					sb.append(new String(doFinal));
				}
				rtnStr=sb.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("**************RSA私钥解密异常*****************" +e);
	        }
			
			System.out.println("*********************RSA私钥最终解密后*********************："+rtnStr);	
		}
		return rtnStr;
	}

	
	/**
	 * 校验数字签名，使用私钥解密后在校验
	 * @param data 排序后的数据
	 * @param privateKey 公钥
	 * @param sign RSA加密后的数字签名
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 此方法不稳定偶尔报错
	 */
	public static boolean checkDataByPrivateKey(String privateKey,String data, String sign) {
		boolean flag=false;
		//用公钥解密
		String decodedData = null;
		System.out.println("***************传进来私钥publicKey*********:"+privateKey);
		System.out.println("***************传进来排序后的字符串***********:"+data);
		System.out.println("***************传进来密文sign**************:"+sign);
		try {
			if(StringUtils.isNotBlank(privateKey)){
				data=MD5Utils.MD5Encode(data);
				decodedData = RSATools.decryptByPrivateKey(sign, privateKey);
				System.out.println("***************解密后字符******************: " + decodedData);
				if(StringUtils.equals(data, decodedData)){
					flag=true;
				}
			}else{
				System.err.println("***************传进来的公钥publicKey为空*********:");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("******************RSA私钥匙解密异常**********************: " +e);
		}
		return flag;
	}
	
	
	/**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     * 
     * @param encodedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * 
     * @return
     * @throws Exception
     */
	public static String sign(String encodedData, String privateKey){
		String rtnStr=StringUtils.EMPTY;
		if(null!=encodedData){
			try {
				// 解密由base64编码的私钥
				byte[] keyBytes = decryptBASE64(privateKey);

				// 构造PKCS8EncodedKeySpec对象
				PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

				// KEY_ALGORITHM 指定的加密算法
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

				// 取私钥匙对象
				PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
				// 用私钥对信息生成数字签名
				Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
				signature.initSign(priKey);
				signature.update(encodedData.getBytes());
				rtnStr=encryptBASE64(signature.sign());
				
			}catch(Exception e){
				System.err.println("**************************签名异常，私钥不合法*******************************************");
			}
		}
		return rtnStr;
	}
	
	
	
	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
//	public static String sign(String encodedData, String privateKey) throws Exception {
//		// 解密由base64编码的私钥
//		byte[] keyBytes = decryptBASE64(privateKey);
//
//		// 构造PKCS8EncodedKeySpec对象
//		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//
//		// KEY_ALGORITHM 指定的加密算法
//		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//
//		// 取私钥匙对象
//		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
//
//		// 用私钥对信息生成数字签名
//		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
//		signature.initSign(priKey);
//		signature.update(encodedData.getBytes());
//
//		return encryptBASE64(signature.sign());
//	}
	

	


	
	/**
     * <p>
     * 校验数字签名
     * </p>
     * 
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     * 
     * @return
     * @throws Exception
     * 
     */
	public static boolean verify(String data, String publicKey, String sign) {
		boolean flag=false;
		if(null!=data){
			try {
				// 解密由base64编码的公钥
				byte[] keyBytes = decryptBASE64(publicKey);

				// 构造X509EncodedKeySpec对象
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

				// KEY_ALGORITHM 指定的加密算法
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

				// 取公钥匙对象
				PublicKey pubKey = keyFactory.generatePublic(keySpec);

				Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
				signature.initVerify(pubKey);
				signature.update(data.getBytes());

				// 验证签名是否正常
				flag=signature.verify(decryptBASE64(sign));
				
			}catch(Exception e){
				System.err.println("**************************验证签名异常，公钥不合法*******************************************");
			}
		}
		return flag;
	}

	
	

	/**
	 * 加密<br>
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
//	public static byte[] encryptByPublicKey2(byte[] data, String key)
//			throws Exception {
//		// 对公钥解密
//		byte[] keyBytes = decryptBASE64(key);
//
//		// 取得公钥
//		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
//		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//		Key publicKey = keyFactory.generatePublic(x509KeySpec);
//
//		// 对数据加密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//
//		return cipher.doFinal(data);
//	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
//	public static byte[] decryptByPrivateKey2(byte[] data, String key)
//			throws Exception {
//		// 对密钥解密
//		byte[] keyBytes = decryptBASE64(key);
//
//		// 取得私钥
//		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
//
//		// 对数据解密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//		cipher.init(Cipher.DECRYPT_MODE, privateKey);
//
//		return cipher.doFinal(data);
//	}




	//测试类 
	public static void main(String[] args) throws Exception {
//		Map<String, Object> keyMap = RSATools.initKey();
//		System.out.println("未再次加密前公"+keyMap.get("RSAPublicKey"));
//		System.out.println("未再次加密前私"+keyMap.get("RSAPrivateKey"));
//		String publicKey1 = RSATools.getPublicKey(keyMap);
//		String privateKey1 = RSATools.getPrivateKey(keyMap);
//		// 公钥生成
//		System.out.println("===============public 公钥生成========================begin");
//		System.out.println("内容是:" + publicKey1);
//		System.out.println("===============public 公钥生成========================end" );
//		// 私钥生成
//		System.out.println("====================private 私钥生成========================begin");
//		System.out.println("对应私钥内容是：======================= : " + privateKey1);
//		System.out.println("================private 私钥生成========================end");

		//String str = "1test for kk11OR2018061316562281140201709215000441100CNY201806131720110test@163.comTest Pay1368888888http://119.23.136.71/CBPayDemo/GW/back.jspCNYs001杭州十日行1100http://119.23.136.71/CBPayDemo/GetPayReturnServletv1.0";
		
		//一队公钥匙和密钥匙
		// String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJTkmxykNYpTFVHiOUU6IA0op0/r5Rn3PKlaJh3ka5JTxLghHZrvdN2DXnNuBdt+EJnVU0pwQjhpt118/zOgMORZwwez/+21HFZqjNYHS6HGO1WBnnvR4FzgkgCFAaOreFVWHahwVDtc42uxf7jkFcwPeiGmsJSu6zmJqWD6WT0bAgMBAAECgYEAkZ+tXav1eZ6dAYNDBEQ/SN90FKThZC39tJ0pHp9j3q/zfStWa0y8flKYNEltjSmVTv/oAKwe+FHdN6CjcXjBl3d0Me8wNg2kmTqLxvDB/s58btVGmC7Nydu48Pg/Fer2ieLZsgF2j8SXIyjF13AnVkDDAbgDCbJT2BDh7TiPqdECQQDIUami9L8fK1hnhGKr9nTaq8XvqIzSY8KnfmMTxjWmCjs3XytryCauxr3G/htZ4SdfNgaq58AdKsoXrcv6xY8XAkEAvkeP2Uvk19ygwAUElpcZqu9OEEoebHzpS7ucakZK3/Un5Lj9RdIrJSZu9PE8UhzIP+vIYME+PfhGuH+w+kDknQJAECnGrkdhRHqS34dnDskMFLjXd8b51eAGzMz94ZKUsZkRvh4HuuKjfGa82VmXi2EHdD8/unqIndHeDJsRYmKecQJAAqCoWt7SbSytx5kKJC43+4VMdasgWm0rJ5GSqmEIHamcAGuRi8pIMD4koBtIsvWUylUuMi/Y9TAdgeS5g+srfQJAfbOa6kQLuGxumdaaslua2WsR820f0X1s+lSjlvf8Nq0E1zt7DY19q7yBpVsdL8RgHQXbsBmrjJfNz2qVyjVKYw==";
		// String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC57QNpxOML4tZ/X4J/uviV2L0OMI4h3kCkFyTcNN7RUnp2AQ/AQhkK7eUc/lTOVHyC2SowxbSuGU9QpSw7Buvjv1T+rLuRuNzFX0eWf0CXyw/gQTT84C4XMtCNuT5Rp5XTytTgJxYnM1BwHC0D7JERgFsvlnx2jnrKV2ojYCBWQIDAQAB";
		
		// 用私钥加密
//		System.out.println("**********************begin 私钥加密 公钥解密***********************************");
//		String encodedData = RSATools.encryptByPrivateKey(str, privateKey);
//		System.out.println("私钥加密后密文: " + encodedData);
//
//		//用公钥解密
//		String decodedData = RSATools.decryptByPublicKey(encodedData, publicKey);
//		System.out.println("公钥解密后: " + decodedData);

//		
//		
//		System.out.println("**********************begin 公钥加密 私钥解密***********************************");
//		// 用公钥加密
//		System.out.println("转为为byte"+new String(str.getBytes()));
//		String encodedData2 = RSATools.encryptByPublicKey(str, publicKey);
//		System.out.println("公钥加密后密文: " + encodedData2);
//
//		// //用私钥解密
//		String decodedData2 = RSATools.decryptByPrivateKey(encodedData2, privateKey);
//		System.out.println("私钥解密后: " + decodedData2);


		 //str="11111201704287100731111v1.0";
		 //开始发送信息
		 //先签名
		// String sign=RSATools.sign(str, privateKey);
		// System.out.println("222222222222222222签名后字符："+sign);
		 //然后用公钥加密
//		 String encry=RSATools.encryptByPublicKey(sign, publicKey1);
//		 System.out.println("1111111111111111加密后字符："+encry);
//		 
//		 //接收信息
//		 //先解密
//		 String decry=RSATools.decryptByPrivateKey(encry, privateKey1);
//		 System.out.println("111111111111111解密后字符："+decry);
//		 //验证签名
		 String str = "1.013T218807867599845CNY0.07201709215000440.07CNY201902180906270.01SGD0.2003446T000success30.0CNY20190218172423PGO20190218206051v1.0";
		 String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTVoUVaRvo0X0XA18Ua2dzIS4Y0HzLxmpFjB5zOT5WoK0c8/2HqiSmBUNeTsSK96HkC01Betdu+auDwMlzb8KLQrJXYxUlHgGMmbIG8Qan4TBweDyifZo/jZ/aRy2SaDnKmIGq6egvKS6QLtwEgGatVCMyY1I6M/4LR9HMJyLzCwIDAQAB";
		 System.out.println("111111111111验证结果："+RSATools.verify(str, publicKey, "123"));
		 
		 
	}

}
