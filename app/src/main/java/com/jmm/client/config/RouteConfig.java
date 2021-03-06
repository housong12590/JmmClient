package com.jmm.client.config;


public enum RouteConfig {

    FORMAL() { // 生产环境

        @Override
        public String getBaseHttpUrl() {
            return "http://ucbgo.com/";
        }

//        @Override
//        public OSSConfig getOSSConfig() {
//            return new OSSConfig(){
//                @Override
//                public String getBucketName() {
//                    return "csh-pic-001";
//                }
//            };
//        }

        @Override
        public String unionPayCode() {
            return "00";
        }
    },

    TEST() { // 测试环境

        @Override
        public String getBaseHttpUrl() {
            return "http://test.ucbgo.com/";
//            return "http://192.168.1.133:8080/";
        }

//        @Override
//        public OSSConfig getOSSConfig() {
//            return new OSSConfig() {
//                @Override
//                public String getBucketName() {
//                    return "csh-test-001";
//                }
//            };
//        }

        @Override
        public String unionPayCode() {
            return "01";
        }
    };

    public abstract String getBaseHttpUrl();

//    public OSSConfig getOSSConfig() {
//        return new OSSConfig();
//    }

    public abstract String unionPayCode();
}
