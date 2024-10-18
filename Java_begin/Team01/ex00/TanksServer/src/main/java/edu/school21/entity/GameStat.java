package edu.school21.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStat {
    private Integer numberClient;
    private Integer shot;
    private Integer hit;
}
