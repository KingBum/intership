package example.springSecurity;

import org.web3j.crypto.Credentials;
import org.web3j.model.SimpleToken;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class SmartContract {
    public static void main(String[] args) throws Exception {
        // Connect to Ethereum node
        Web3j web3j = Web3j.build(new HttpService("https://data-seed-prebsc-1-s1.binance.org:8545/"));

        // Replace with your actual private key
        String privateKey = "0x28f7d1908d2588ce1a697a0979ccc89ca0054388eda4109da0282eb69700bd2f";
        Credentials credentials = Credentials.create(privateKey);
        BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L); // Set your desired gas price
        BigInteger gasLimit = BigInteger.valueOf(6_300_000); // Set your desired gas limit

//         Create a custom gas provider with the specified gas limit
        StaticGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);

        SimpleToken newContract;
        try {
            // Use the default TransactionManager provided by Web3j
            newContract = SimpleToken.deploy(web3j, credentials, gasProvider).send();
            System.out.println(newContract);

            // Your contract has been deployed successfully
        } catch (Exception e) {
            throw new RuntimeException("Failed to deploy contract: " + e.getMessage(), e);
        }
        var contractAddress = newContract.getContractAddress();
        System.out.println(contractAddress);
//        String contractAddress = "0xfb29bb913f5b8452a77dae217f1a25ed3b040752";
        SimpleToken simpleToken = SimpleToken.load(contractAddress, web3j, credentials, gasProvider);
        BigInteger getBalnce = simpleToken.balanceOf("0x3f75b37F3553e890fB81087ee47df3F883ee451b").send();
            System.out.println(getBalnce);
    }
}
