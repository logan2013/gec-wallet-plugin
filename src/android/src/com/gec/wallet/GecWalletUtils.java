package com.gec.wallet;

import static org.web3j.crypto.Hash.sha256;

import java.io.IOException;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.utils.Numeric;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GecWalletUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String generate(String mnemonic, String password) {
		try {
			byte[] seed = GecMnemonicUtils.generateSeed(mnemonic.trim(), password.trim());
			ECKeyPair privateKey = ECKeyPair.create(sha256(seed));

			return generateWalletFile(password, privateKey);

		} catch (Throwable e) {
			return null;
		}
	}

	public static String generateByPk(String privateKey, String password) {
		try {
			ECKeyPair privateKeyPair = ECKeyPair.create(Numeric.hexStringToByteArray(privateKey));
			
			return generateWalletFile(password, privateKeyPair);
			
		} catch (Throwable e) {
			return null;
		}
	}

	public static String load(String keystore, String password) {
		try {
			WalletFile walletFile = objectMapper.readValue(keystore.trim(), WalletFile.class);
			return Numeric.toHexStringNoPrefix(Wallet.decrypt(password.trim(), walletFile).getPrivateKey());
		} catch (Throwable e) {
			return null;
		}
	}

	private static String generateWalletFile(String password, ECKeyPair ecKeyPair) throws CipherException, IOException {

		WalletFile walletFile = Wallet.createLight(password, ecKeyPair);

		return objectMapper.writeValueAsString(walletFile);
	}

}
