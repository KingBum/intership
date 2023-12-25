package example.springSecurity;

import example.springSecurity.controller.ContractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@SpringBootApplication
@EnableScheduling
public class SpringSecurityApplication {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}
	@Bean
	public Web3j web3j() {
		// Connect to Ethereum node
		return Web3j.build(new HttpService(env.getProperty("RPC")));
	}

	@Bean
	public Credentials credentials() {
		// Replace with your actual private key
		String privateKey = env.getProperty("PRIV_KEY");
		return Credentials.create(privateKey);
	}

	@Bean
	public StaticGasProvider gasProvider() {
		BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L); // Set your desired gas price
		BigInteger gasLimit = BigInteger.valueOf(6_300_000); // Set your desired gas limit
		return new StaticGasProvider(gasPrice, gasLimit);
	}

	@Bean
	public ContractController contractController(Web3j web3j, Credentials credentials, StaticGasProvider gasProvider) {
		// Replace with your actual contract address
		String contractAddress = env.getProperty("ADDRESS_CONTRACT");
		return new ContractController(web3j, credentials, contractAddress, gasProvider);
	}
}
