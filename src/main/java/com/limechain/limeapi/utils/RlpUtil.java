package com.limechain.limeapi.utils;

import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;

import java.util.Set;
import java.util.stream.Collectors;

public class RlpUtil {
    public static String mapToRlpHex(Set<String> set) {
        RlpList rlpList = new RlpList(
                set.stream()
                        .map(RlpString::create) // Convert each transactionHash to an RLP string
                        .collect(Collectors.toList()));

        byte[] rlpEncoded = RlpEncoder.encode(rlpList);
        return "0x" + bytesToHex(rlpEncoded);

    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
