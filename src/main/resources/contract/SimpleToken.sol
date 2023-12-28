// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract SimpleToken {
    uint8 public decimals = 18;
    uint256 public totalSupply;

    mapping(address => uint256) public balanceOf;

    event Transfer(address indexed from, address indexed to, uint256 value);
    event Mint(address indexed to, uint256 value);
    event Burn(address indexed from, uint256 value);

    address public owner;

    modifier onlyOwner() {
        require(msg.sender == owner, "Only the owner can call this function");
        _;
    }

    constructor() {
        owner = msg.sender;
        totalSupply = 1000000 * 10**decimals;
        balanceOf[msg.sender] = totalSupply;
    }

    function transfer(address to, uint256 value) public returns (bool) {
        require(balanceOf[msg.sender] >= value, "Insufficient balance");
        require(to != address(0), "Invalid recipient address");

        balanceOf[msg.sender] -= value;
        balanceOf[to] += value;

        emit Transfer(msg.sender, to, value);

        return true;
    }

    function mint(address to, uint256 value) public onlyOwner {
        require(to != address(0), "Invalid recipient address");

        totalSupply += value;
        balanceOf[to] += value;

        emit Mint(to, value);
    }

    function burn(uint256 value) public {
        require(balanceOf[msg.sender] >= value, "Insufficient balance");

        totalSupply -= value;
        balanceOf[msg.sender] -= value;

        emit Burn(msg.sender, value);
    }

    function updateDecimals(uint8 newDecimals) public onlyOwner {
        decimals = newDecimals;
    }

    function transferOwnership(address newOwner) public onlyOwner {
        require(newOwner != address(0), "Invalid new owner address");
        owner = newOwner;
    }
}
