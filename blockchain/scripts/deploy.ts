const { ethers } = require("hardhat");

async function main() {
  const radiationMonitor = await ethers.deployContract("RadiationMonitor");

  await radiationMonitor.waitForDeployment();

  console.log(
    `RadiationMonitor deployed to ${radiationMonitor.target}`
  );
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
