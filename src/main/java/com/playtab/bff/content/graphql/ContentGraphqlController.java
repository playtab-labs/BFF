package com.playtab.bff.content.graphql;

import com.playtab.bff.content.dto.output.*;
import com.playtab.bff.content.service.ContentFacade;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ContentGraphqlController {

    private final ContentFacade contentFacade;

    public ContentGraphqlController(ContentFacade contentFacade) {
        this.contentFacade = contentFacade;
    }

    @QueryMapping
    public List<FoodTruckDto> foodTrucks(@Argument String locale) {
        return contentFacade.getFoodTrucks(locale);
    }

    @QueryMapping
    public List<PubDto> pubs(@Argument String locale) {
        return contentFacade.getPubs(locale);
    }

    @QueryMapping
    public MdItemsResponseDto mdItems(
            @Argument String locale,
            @Argument Integer page,
            @Argument Integer size
    ) {
        return contentFacade.getMdItems(locale, page, size);
    }

    @QueryMapping
    public MdItemDetailDto mdItemDetail(
            @Argument String locale,
            @Argument Long mdItemId
    ) {
        return contentFacade.getMdItemDetail(locale, mdItemId);
    }

    @QueryMapping
    public NoticesResponseDto notices(
            @Argument String locale,
            @Argument Integer page,
            @Argument Integer size
    ) {
        return contentFacade.getNotices(locale, page, size);
    }

    @QueryMapping
    public NoticeDetailDto noticeDetail(
            @Argument String locale,
            @Argument Long noticeId
    ) {
        return contentFacade.getNoticeDetail(locale, noticeId);
    }
}
