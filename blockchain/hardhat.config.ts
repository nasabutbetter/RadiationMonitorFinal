require("@nomicfoundation/hardhat-toolbox");

const SEPOLIA_RPC_URL = "https://ethereum-sepolia-rpc.publicnode.com";
const PRIVATE_KEY = "0xb720673a9055c80484aacdc810a59505d4d062ec469f058df7829a33769f9962";

module.exports = {
  solidity: "0.8.24",
  networks: {
    sepolia: {
      url: SEPOLIA_RPC_URL,
      accounts: [PRIVATE_KEY],
    },
  },
};
