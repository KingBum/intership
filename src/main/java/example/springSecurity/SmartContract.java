package example.springSecurity;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

public class SmartContract {

    public static void main(String[] args) throws Exception {
        // Ethereum node URL
        String ethereumNodeUrl = "https://data-seed-prebsc-1-s1.binance.org:8545/";

        // Replace with your Ethereum account private key
        String privateKey = "0x28f7d1908d2588ce1a697a0979ccc89ca0054388eda4109da0282eb69700bd2f";
//        String privateKey = "6S7QS9VTBIJKM7IKS82F7ARZCUDMCGW4UJ";

        // Ethereum smart contract address
        String contractAddress = "0xC7b0eBd5b7D59e7441a461700F158CBade7e9Bd2";

        // Connect to Ethereum node
        Web3j web3j = Web3j.build(new HttpService(ethereumNodeUrl));

        // Load your Ethereum account credentials
        TransactionManager transactionManager = new ClientTransactionManager(web3j, privateKey);
        System.out.println(transactionManager);
        // Load your smart contract
        SimpleToken simpleToken = SimpleToken.load(
                contractAddress,
                web3j,
                transactionManager,
                new DefaultGasProvider()
        );
        try {
            String name = simpleToken.name().send();
            System.out.println("Name of address: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String name = simpleToken.name().send();
//        System.out.println("name of address: " + name);
        // Example: Call a function that returns a value
//        String targetAddress = "0x3f75b37F3553e890fB81087ee47df3F883ee451b";
//        try {
//            BigInteger balance = simpleToken.balanceOf(targetAddress).send();
//            System.out.println("Balance of address: " + balance);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//         Example: Send a transaction to transfer tokens
//        try {
//            String toAddress = "0xa487c0Af712Ff3bD756e11F6a7e125aA5b9390c0";
//            BigInteger amount = BigInteger.valueOf(100);
//
//            // Send transaction
//            simpleToken.transfer(toAddress, amount).send();
//
//            // Display success message
//            System.out.println("Transfer successful.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
        // Example: Get the total supply of tokens
//        try {
//            BigInteger totalSupply = simpleToken.totalSupply().send();
//            System.out.println("Total supply of tokens: " + totalSupply);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
