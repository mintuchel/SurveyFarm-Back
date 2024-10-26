package notblank.boatvote.domain.answer.dto.response;

import java.util.List;

public class SAResultResponse implements ResultResponse{

    List<String> list;

    public SAResultResponse(List<String> list){
        this.list = list;
    }
}
