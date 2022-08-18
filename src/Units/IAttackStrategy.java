package Units;

import Strategy.AttackStrategy;
import Units.Unit;

public interface IAttackStrategy {
    Unit AcceptStrategy(AttackStrategy attackStrategy);
}
