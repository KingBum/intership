const { ethers } = require('hardhat') ;

async function main() {
  const UDANFT = await ethers.getContractFactory("GameItem")
  const udanft = await UDANFT.deploy()

  const addressContract = await udanft.getAddress()

  console.log('Contract deployed to address: ', addressContract);

  await udanft.awardItem("https://plum-wooden-rattlesnake-570.mypinata.cloud/ipfs/QmXKbs1bJR43FL5y3M19TTQMDYhDC2boFqWbysGDpgxG9t/1.json", { gasLimit: 2000000 })
  await udanft.awardItem("https://plum-wooden-rattlesnake-570.mypinata.cloud/ipfs/QmXKbs1bJR43FL5y3M19TTQMDYhDC2boFqWbysGDpgxG9t/2.json", { gasLimit: 2000000 })
  await udanft.awardItem("https://plum-wooden-rattlesnake-570.mypinata.cloud/ipfs/QmXKbs1bJR43FL5y3M19TTQMDYhDC2boFqWbysGDpgxG9t/3.json", { gasLimit: 2000000 })
  await udanft.awardItem("https://plum-wooden-rattlesnake-570.mypinata.cloud/ipfs/QmXKbs1bJR43FL5y3M19TTQMDYhDC2boFqWbysGDpgxG9t/4.json", { gasLimit: 2000000 })
  await udanft.awardItem("https://plum-wooden-rattlesnake-570.mypinata.cloud/ipfs/QmXKbs1bJR43FL5y3M19TTQMDYhDC2boFqWbysGDpgxG9t/5.json", { gasLimit: 2000000 })


  console.log("NFT succesfully awardItem")
}

// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
main()
  .then(() => process.exit(0))
  .catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
