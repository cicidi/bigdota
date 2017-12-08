package com.cicidi.bigdota.validator;

import com.cicidi.bigdota.domain.dota.MatchReplay;
import com.cicidi.validation.Validator;

public class MatchDataValidator implements Validator<MatchReplay> {
    @Override
    public boolean validate(MatchReplay matchReplay) {
        if (matchReplay.getData() == null || matchReplay.getData().contains("+0")) return false;
        return true;
    }
}
