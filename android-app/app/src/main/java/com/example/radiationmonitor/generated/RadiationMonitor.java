package com.example.radiationmonitor.generated;

import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.protocol.Web3j;
import org.web3j.crypto.Credentials;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.utils.Collection;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RadiationMonitor extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b50610619806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80632fa96b2714610046578063379d14091461007657806365059e3514610092575b600080fd5b610060600480360381019061005b919061038d565b6100c4565b60405161006d91906104d3565b60405180910390f35b610090600480360381019061008b9190610521565b6101cb565b005b6100ac60048036038101906100a7919061054e565b6102c3565b6040516100bb939291906105ac565b60405180910390f35b60606000808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020805480602002602001604051908101604052809291908181526020016000905b828210156101c0578382906000526020600020906003020160405180606001604052908160008201548152602001600182015481526020016002820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152505081526020019060010190610124565b505050509050919050565b6000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060405180606001604052808381526020014281526020013373ffffffffffffffffffffffffffffffffffffffff168152509080600181540180825580915050600190039060005260206000209060030201600090919091909150600082015181600001556020820151816001015560408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505050565b600060205281600052604060002081815481106102df57600080fd5b9060005260206000209060030201600091509150508060000154908060010154908060020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905083565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061035a8261032f565b9050919050565b61036a8161034f565b811461037557600080fd5b50565b60008135905061038781610361565b92915050565b6000602082840312156103a3576103a261032a565b5b60006103b184828501610378565b... [truncated]
";

    public static final String FUNC_GETREADINGSFORUSER = "getReadingsForUser";
    public static final String FUNC_RECORDEXPOSURE = "recordExposure";

    @Deprecated
    protected RadiationMonitor(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RadiationMonitor(String contractAddress, Web3j web3j, TransactionManager transactionManager, org.web3j.tx.gas.GasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, gasProvider);
    }

    @Deprecated
    protected RadiationMonitor(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public RadiationMonitor(String contractAddress, Web3j web3j, Credentials credentials, org.web3j.tx.gas.GasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, credentials, gasProvider);
    }

    public RemoteFunctionCall<List<Reading>> getReadingsForUser(String user) {
        final Function function = new Function(FUNC_GETREADINGSFORUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Reading>>() {}));
        return executeRemoteCallMultipleValueReturn(function, Collections.singletonList(Reading.class));
    }

    public RemoteFunctionCall<TransactionReceipt> recordExposure(BigInteger radiationLevel) {
        final Function function = new Function(
                FUNC_RECORDEXPOSURE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(radiationLevel)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static RadiationMonitor load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RadiationMonitor(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    @Deprecated
    public static RadiationMonitor load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RadiationMonitor(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static RadiationMonitor load(String contractAddress, Web3j web3j, TransactionManager transactionManager, org.web3j.tx.gas.GasProvider gasProvider) {
        return new RadiationMonitor(contractAddress, web3j, transactionManager, gasProvider);
    }

    public static RadiationMonitor load(String contractAddress, Web3j web3j, Credentials credentials, org.web3j.tx.gas.GasProvider gasProvider) {
        return new RadiationMonitor(contractAddress, web3j, credentials, gasProvider);
    }

    public static RemoteFunctionCall<RadiationMonitor> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RadiationMonitor.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteFunctionCall<RadiationMonitor> deploy(Web3j web3j, Credentials credentials, org.web3j.tx.gas.GasProvider gasProvider) {
        return deployRemoteCall(RadiationMonitor.class, web3j, credentials, gasProvider, BINARY, "");
    }

    public static RemoteFunctionCall<RadiationMonitor> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RadiationMonitor.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteFunctionCall<RadiationMonitor> deploy(Web3j web3j, TransactionManager transactionManager, org.web3j.tx.gas.GasProvider gasProvider) {
        return deployRemoteCall(RadiationMonitor.class, web3j, transactionManager, gasProvider, BINARY, "");
    }

    public static class Reading extends StaticStruct {
        public BigInteger radiationLevel;

        public BigInteger timestamp;

        public String user;

        public Reading(BigInteger radiationLevel, BigInteger timestamp, String user) {
            super(new Uint256(radiationLevel), 
                    new Uint256(timestamp), 
                    new Address(user));
            this.radiationLevel = radiationLevel;
            this.timestamp = timestamp;
            this.user = user;
        }

        public Reading(Uint256 radiationLevel, Uint256 timestamp, Address user) {
            super(radiationLevel, timestamp, user);
            this.radiationLevel = radiationLevel.getValue();
            this.timestamp = timestamp.getValue();
            this.user = user.getValue();
        }

        @Override
        public List<Type<?>> get   StructuralComponents() {
            return Collections.emptyList();
        }
    }
}
