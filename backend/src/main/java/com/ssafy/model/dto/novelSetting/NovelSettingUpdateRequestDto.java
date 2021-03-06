package com.ssafy.model.dto.novelSetting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NovelSettingUpdateRequestDto {
    private String novelSettingName;
    private String novelSettingText;
}
