package cars.criteria;

import cars.model.OrderBy;
import cars.model.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarCriteria {
    private String brand="";
    private int maxAge=200;
    private OrderBy orderBy=OrderBy.id;
    private OrderType orderType=OrderType.asc;
}
