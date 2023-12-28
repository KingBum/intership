package com.spring.finaldemo.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.10.3.
 */
@SuppressWarnings("rawtypes")
public class SimpleToken extends Contract {
    public static final String BINARY = "60806040525f805460ff1916601217905534801561001b575f80fd5b50600380546001600160a01b031916331790555f5461003e9060ff16600a61015a565b61004b90620f424061016f565b6001819055335f90815260026020526040902055610186565b634e487b7160e01b5f52601160045260245ffd5b600181815b808511156100b257815f190482111561009857610098610064565b808516156100a557918102915b93841c939080029061007d565b509250929050565b5f826100c857506001610154565b816100d457505f610154565b81600181146100ea57600281146100f457610110565b6001915050610154565b60ff84111561010557610105610064565b50506001821b610154565b5060208310610133831016604e8410600b8410161715610133575081810a610154565b61013d8383610078565b805f190482111561015057610150610064565b0290505b92915050565b5f61016860ff8416836100ba565b9392505050565b808202811582820484141761015457610154610064565b6106c8806101935f395ff3fe608060405234801561000f575f80fd5b5060043610610090575f3560e01c806342966c681161006357806342966c68146100f657806370a08231146101095780638da5cb5b14610128578063a9059cbb14610153578063f2fde38b14610176575f80fd5b80630ce83a611461009457806318160ddd146100a9578063313ce567146100c557806340c10f19146100e3575b5f80fd5b6100a76100a2366004610579565b610189565b005b6100b260015481565b6040519081526020015b60405180910390f35b5f546100d19060ff1681565b60405160ff90911681526020016100bc565b6100a76100f13660046105bb565b6101d1565b6100a76101043660046105e3565b6102d1565b6100b26101173660046105fa565b60026020525f908152604090205481565b60035461013b906001600160a01b031681565b6040516001600160a01b0390911681526020016100bc565b6101666101613660046105bb565b610397565b60405190151581526020016100bc565b6100a76101843660046105fa565b6104d7565b6003546001600160a01b031633146101bc5760405162461bcd60e51b81526004016101b390610613565b60405180910390fd5b5f805460ff191660ff92909216919091179055565b6003546001600160a01b031633146101fb5760405162461bcd60e51b81526004016101b390610613565b6001600160a01b03821661024d5760405162461bcd60e51b8152602060048201526019602482015278496e76616c696420726563697069656e74206164647265737360381b60448201526064016101b3565b8060015f82825461025e919061066c565b90915550506001600160a01b0382165f908152600260205260408120805483929061028a90849061066c565b90915550506040518181526001600160a01b038316907f0f6798a560793a54c3bcfe86a93cde1e73087d944c0ea20544137d41213968859060200160405180910390a25050565b335f908152600260205260409020548111156103265760405162461bcd60e51b8152602060048201526014602482015273496e73756666696369656e742062616c616e636560601b60448201526064016101b3565b8060015f828254610337919061067f565b9091555050335f908152600260205260408120805483929061035a90849061067f565b909155505060405181815233907fcc16f5dbb4873280815c1ee09dbd06736cffcc184412cf7a71a0fdb75d397ca59060200160405180910390a250565b335f908152600260205260408120548211156103ec5760405162461bcd60e51b8152602060048201526014602482015273496e73756666696369656e742062616c616e636560601b60448201526064016101b3565b6001600160a01b03831661043e5760405162461bcd60e51b8152602060048201526019602482015278496e76616c696420726563697069656e74206164647265737360381b60448201526064016101b3565b335f908152600260205260408120805484929061045c90849061067f565b90915550506001600160a01b0383165f908152600260205260408120805484929061048890849061066c565b90915550506040518281526001600160a01b0384169033907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9060200160405180910390a35060015b92915050565b6003546001600160a01b031633146105015760405162461bcd60e51b81526004016101b390610613565b6001600160a01b0381166105575760405162461bcd60e51b815260206004820152601960248201527f496e76616c6964206e6577206f776e657220616464726573730000000000000060448201526064016101b3565b600380546001600160a01b0319166001600160a01b0392909216919091179055565b5f60208284031215610589575f80fd5b813560ff81168114610599575f80fd5b9392505050565b80356001600160a01b03811681146105b6575f80fd5b919050565b5f80604083850312156105cc575f80fd5b6105d5836105a0565b946020939093013593505050565b5f602082840312156105f3575f80fd5b5035919050565b5f6020828403121561060a575f80fd5b610599826105a0565b60208082526025908201527f4f6e6c7920746865206f776e65722063616e2063616c6c20746869732066756e60408201526431ba34b7b760d91b606082015260800190565b634e487b7160e01b5f52601160045260245ffd5b808201808211156104d1576104d1610658565b818103818111156104d1576104d161065856fea2646970667358221220196850023a1169e17505a2b24dbb8057a3a08d5a4cfd449ce697ddf2d010aac964736f6c63430008140033";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURN = "burn";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPDATEDECIMALS = "updateDecimals";

    public static final Event BURN_EVENT = new Event("Burn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event MINT_EVENT = new Event("Mint", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected SimpleToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SimpleToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SimpleToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SimpleToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<BurnEventResponse> getBurnEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(BURN_EVENT, transactionReceipt);
        ArrayList<BurnEventResponse> responses = new ArrayList<BurnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BurnEventResponse typedResponse = new BurnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static BurnEventResponse getBurnEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(BURN_EVENT, log);
        BurnEventResponse typedResponse = new BurnEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<BurnEventResponse> burnEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getBurnEventFromLog(log));
    }

    public Flowable<BurnEventResponse> burnEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BURN_EVENT));
        return burnEventFlowable(filter);
    }

    public static List<MintEventResponse> getMintEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINT_EVENT, transactionReceipt);
        ArrayList<MintEventResponse> responses = new ArrayList<MintEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MintEventResponse typedResponse = new MintEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MintEventResponse getMintEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINT_EVENT, log);
        MintEventResponse typedResponse = new MintEventResponse();
        typedResponse.log = log;
        typedResponse.to = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<MintEventResponse> mintEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMintEventFromLog(log));
    }

    public Flowable<MintEventResponse> mintEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINT_EVENT));
        return mintEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String param0) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> burn(BigInteger value) {
        final Function function = new Function(
                FUNC_BURN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String to, BigInteger value) {
        final Function function = new Function(
                FUNC_MINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateDecimals(BigInteger newDecimals) {
        final Function function = new Function(
                FUNC_UPDATEDECIMALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(newDecimals)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static SimpleToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SimpleToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SimpleToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SimpleToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SimpleToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SimpleToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SimpleToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SimpleToken.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SimpleToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SimpleToken.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SimpleToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SimpleToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class BurnEventResponse extends BaseEventResponse {
        public String from;

        public BigInteger value;
    }

    public static class MintEventResponse extends BaseEventResponse {
        public String to;

        public BigInteger value;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }
}
