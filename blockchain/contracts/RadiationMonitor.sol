// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

contract RadiationMonitor {
    struct Reading {
        uint256 radiationLevel;
        uint256 timestamp;
        address user;
    }

    mapping(address => Reading[]) public readings;

    function recordExposure(uint256 radiationLevel) public {
        readings[msg.sender].push(Reading(radiationLevel, block.timestamp, msg.sender));
    }

    function getReadingsForUser(address user) public view returns (Reading[] memory) {
        return readings[user];
    }
}
