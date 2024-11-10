package com.tsoftware.qtd.constants.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RatingLevel {
	AA("rất tốt"),A("tốt"),BB("trung bình"),B("thấp"),CC("yếu"),C("NO");
	private final String description;
}
