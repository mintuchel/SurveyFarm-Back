package notblank.boatvote.domain.answer.dto.response;

import java.util.List;

public class MCResultResponse implements ResultResponse{

    List<Integer> list;

    public MCResultResponse(List<Integer> list){
        this.list = list;
    }
}
