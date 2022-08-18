# Defend or Destroy Game built using [Java & JavaFX GUI]
The objective of this game is to destroy the other player. The player will have 2 options: attacks or defend.

## Introduction
Destroy or Defend (DorD) is a tower defense multi-player board game. On a massive 10,000 by 10,000 square arena, a defender aspires to protect their main base and an attacker aspires to destroy that base. The defender uses a combination of stationary towers (supporting turrets, cannons, sentinels and guns) and patrolling forces (consisting of soldiers, tanks and other vehicles) to protect its base. An attacker uses marching armies (consisting of soldiers, tanks and other vehicles) to attack the defender’s units, towers and base. Given a preset time, the attacker must complete their task by successfully destroying the base and win; otherwise, the defender receives backup units, destroys the attacker units and successfully defends the base and win.
There are two teams in the arena, an attacking team and a defending team. Each team comprises one or more players. You are one of those players and must successfully destroy or defend the base, depending on which team you are on. The game consists of only one run. Before the beginning of the run, each player receives a preset number of “points” which they can use to buy and setup their defending or attacking units. Each unit is also given an autonomous tactic which will be followed throughout the run. A player is allowed to setup their units on certain regions of the arena. Once the run starts, the game units behave autonomously (without any intervention from the player). The units attack or defend following their assigned tactics. The run continues until one of the following occurs where one victor is declared:
* The base is destroyed, regardless of how many defending units are still alive;
* The attacker units are destroyed and no more attacking units are available; or
* The preset time is up, which means the attack has failed.

## Specifics
 - Before the beginning of the run, players receive “Points” which are used to setup their units;
 - Players buy and upgrade units and other skills;
 - Players deploy units in designated regions in the arena;
 - Units have health values which are lost due to being attacked; once its health is depleted, a unit is destroyed;
 - Attackers team units’ move autonomously in the arena generally towards the defender base following preset tactic;
 - Defenders team units’ might be stationary or patrolling, they also behave autonomously by deciding which attacking units to target.
 - Units can behave following one of these tactics:
 - Attack units with lowest health;
 - Attack units with highest damage;
 - Attack randomly;
 - Attack units with a predefined priority (**ex:** player will say: attack the “Car bomb” first and then focus on the RBG soldiers and so on...).
 - Units have attack damages, target types, attack speeds and bleeding sources1;
   * **Ex:** Tesla Tank: has 300 attack damage and it can attack other tanks and soldiers, but it can’t attack structures. It can at attack 3 tanks each 10 seconds. Snipers can’t attack it.
 - Units have attack speed which tells them how often they can attack:
   * **Ex:** sniper attack speed’s is 1 which means they can perform one attack per second.
 - Unit have armor: a discounting factor of received damage:
   * **Ex:** attack dmg = 300, armor = 0.4 => total damage dealt to the unit is (300 – (300*0.4) =180).
 - Units can’t move over each other:
   * **Ex:** if there is a tank wants to move and there is another unit in its path, then this unit should find another path, if it’s stuck then it will stand in its place.

**_NOTE: The only parameter of the game is the initial “Points” offered to each player at the beginning of the game._**