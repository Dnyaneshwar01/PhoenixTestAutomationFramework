package com.api.response.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateJobResponseModel {
    private String message;
    private CreateJobDataModel data;
}
