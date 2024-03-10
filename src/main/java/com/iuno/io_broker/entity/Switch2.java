package com.iuno.io_broker.entity;

import lombok.Data;

@Data
public class Switch2 {
    private boolean leftClick;
    private boolean rightClick;
    private boolean leftDoubleClick;
    private boolean rightDoubleClick;
    private boolean leftLongClick;
    private boolean rightLongClick;
    private boolean bothClick;
    private boolean bothDoubleClick;
    private boolean bothLongClick;
}
