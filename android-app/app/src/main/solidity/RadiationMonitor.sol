
// SPDX-License-Identifier: MIT
pragma solidity >=0.8.0 <0.9.0;

contract RadiationMonitor {
    struct Reading {
        uint256 radiationLevel;
        uint256 timestamp;
        address user;
    }

    mapping(address => Reading[]) public readings;

    event ExposureRecorded(
        address indexed user,
        uint256 radiationLevel,
        uint256 timestamp
    );

    function recordExposure(uint256 radiationLevel) public {
        readings[msg.sender].push(
            Reading(radiationLevel, block.timestamp, msg.sender)
        );
        emit ExposureRecorded(msg.sender, radiationLevel, block.timestamp);
    }

    function getReadingsForUser(
        address user
    ) public view returns (Reading[] memory) {
        return readings[user];
    }
}
