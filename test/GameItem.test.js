// test/GameItem.test.js
const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("GameItem", function () {
  let gameItemContract;
  let owner;

  beforeEach(async function () {
    const GameItem = await ethers.getContractFactory("GameItem");
    [owner] = await ethers.getSigners();
    gameItemContract = await GameItem.deploy();

  });

  it("should set right owner", async function () {
    const tokenId = 0; // Assuming the first token ID is 0
    expect(await gameItemContract.ownerOf(tokenId)).to.equal(owner.address);
});



});
