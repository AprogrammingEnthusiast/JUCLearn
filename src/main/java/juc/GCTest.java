/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package juc;

/**
 * 有趣的GC实验
 * VM options : -verbose:gc
 * showByteCode : -v -c -l $FileClass$ / $OutputPath$
 * @author wb-wj449816
 * @version $Id: GCTest.java, v 0.1 2020年05月13日 13:59 wb-wj449816 Exp $
 */
public class GCTest {

    public static void main(String[] args) {

        if (true) {
            byte[] bytes = new byte[1024 * 1024 * 64];
            System.out.println(bytes.length / 1024);
        }
        System.gc();
    }

}