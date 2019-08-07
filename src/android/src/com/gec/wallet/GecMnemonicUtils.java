package com.gec.wallet;

import java.nio.charset.Charset;

import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

public class GecMnemonicUtils {

	private static final int SEED_ITERATIONS = 2048;
	private static final int SEED_KEY_SIZE = 512;

	/**
	 * To create a binary seed from the mnemonic, we use the PBKDF2 function with a
	 * mnemonic sentence (in UTF-8 NFKD) used as the password and the string
	 * "mnemonic" + passphrase (again in UTF-8 NFKD) used as the salt. The iteration
	 * count is set to 2048 and HMAC-SHA512 is used as the pseudo-random function.
	 * The length of the derived key is 512 bits (= 64 bytes).
	 *
	 * @param mnemonic
	 *            The input mnemonic which should be 128-160 bits in length
	 *            containing only valid words
	 * @param passphrase
	 *            The passphrase which will be used as part of salt for PBKDF2
	 *            function
	 * @return Byte array representation of the generated seed
	 */
	public static byte[] generateSeed(String mnemonic, String passphrase) {
		validateMnemonic(mnemonic);
		passphrase = passphrase == null ? "" : passphrase;

		String salt = String.format("mnemonic%s", passphrase);
		PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA512Digest());
		gen.init(mnemonic.getBytes(Charset.forName("UTF-8")), salt.getBytes(Charset.forName("UTF-8")), SEED_ITERATIONS);

		return ((KeyParameter) gen.generateDerivedParameters(SEED_KEY_SIZE)).getKey();
	}

	private static void validateMnemonic(String mnemonic) {
		if (mnemonic == null || mnemonic.trim().isEmpty()) {
			throw new IllegalArgumentException("Mnemonic is required to generate a seed");
		}
	}
}
