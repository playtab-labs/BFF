package com.playtab.bff.content.service;

import com.playtab.bff.content.dto.output.*;
import com.playtab.bff.grpc.client.ContentGrpcClient;
import com.playtab.contentservice.grpc.proto.v1.*;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ContentFacade {

    private static final String DEFAULT_LOCALE = "ko";

    private final ContentGrpcClient contentGrpcClient;

    public ContentFacade(ContentGrpcClient contentGrpcClient) {
        this.contentGrpcClient = contentGrpcClient;
    }

    public List<FoodTruckDto> getFoodTrucks(String locale) {
        GetFoodTrucksRequest request = GetFoodTrucksRequest.newBuilder()
                .setLocale(resolveLocale(locale))
                .setOnlyVisible(true)
                .build();

        GetFoodTrucksResponse response = contentGrpcClient.getFoodTrucks(request);
        return response.getFoodTrucksList().stream()
                .map(this::toFoodTruckDto)
                .toList();
    }

    public List<PubDto> getPubs(String locale) {
        GetPubsRequest request = GetPubsRequest.newBuilder()
                .setLocale(resolveLocale(locale))
                .setOnlyVisible(true)
                .build();

        GetPubsResponse response = contentGrpcClient.getPubs(request);
        return response.getPubsList().stream()
                .map(this::toPubDto)
                .toList();
    }

    public MdItemsResponseDto getMdItems(String locale, Integer page, Integer size) {
        GetMdItemsRequest.Builder builder = GetMdItemsRequest.newBuilder()
                .setLocale(resolveLocale(locale))
                .setOnlyVisible(true)
                .setPageRequest(PageRequest.newBuilder()
                        .setPage(page != null ? page : 0)
                        .setSize(size != null ? size : 20)
                        .build());

        GetMdItemsResponse response = contentGrpcClient.getMdItems(builder.build());

        MdItemsResponseDto dto = new MdItemsResponseDto();
        dto.setItems(response.getItemsList().stream()
                .map(this::toMdItemSummaryDto)
                .toList());
        dto.setPageInfo(toPageInfoDto(response.getPageInfo()));
        return dto;
    }

    public MdItemDetailDto getMdItemDetail(String locale, long mdItemId) {
        GetMdItemDetailRequest request = GetMdItemDetailRequest.newBuilder()
                .setLocale(resolveLocale(locale))
                .setMdItemId(mdItemId)
                .build();

        GetMdItemDetailResponse response = contentGrpcClient.getMdItemDetail(request);
        return toMdItemDetailDto(response.getItem());
    }

    public NoticesResponseDto getNotices(String locale, Integer page, Integer size) {
        GetNoticesRequest.Builder builder = GetNoticesRequest.newBuilder()
                .setLocale(resolveLocale(locale))
                .setOnlyVisible(true)
                .setPageRequest(PageRequest.newBuilder()
                        .setPage(page != null ? page : 0)
                        .setSize(size != null ? size : 20)
                        .build());

        GetNoticesResponse response = contentGrpcClient.getNotices(builder.build());

        NoticesResponseDto dto = new NoticesResponseDto();
        dto.setNotices(response.getNoticesList().stream()
                .map(this::toNoticeSummaryDto)
                .toList());
        dto.setPageInfo(toPageInfoDto(response.getPageInfo()));
        return dto;
    }

    public NoticeDetailDto getNoticeDetail(String locale, long noticeId) {
        GetNoticeDetailRequest request = GetNoticeDetailRequest.newBuilder()
                .setLocale(resolveLocale(locale))
                .setNoticeId(noticeId)
                .build();

        GetNoticeDetailResponse response = contentGrpcClient.getNoticeDetail(request);
        return toNoticeDetailDto(response.getNotice());
    }

    // ── Proto → DTO 변환 ──

    private FoodTruckDto toFoodTruckDto(FoodTruckItem proto) {
        FoodTruckDto dto = new FoodTruckDto();
        dto.setId(proto.getId());
        dto.setName(proto.getName());
        dto.setThumbnailImageUrl(proto.getThumbnailImageUrl());
        dto.setShortDescription(proto.getShortDescription());
        dto.setDisplayOrder(proto.getDisplayOrder());
        return dto;
    }

    private PubDto toPubDto(PubItem proto) {
        PubDto dto = new PubDto();
        dto.setId(proto.getId());
        dto.setCollegeName(proto.getCollegeName());
        dto.setThumbnailImageUrl(proto.getThumbnailImageUrl());
        dto.setNameConfirmed(proto.getIsNameConfirmed());
        dto.setDisplayOrder(proto.getDisplayOrder());
        return dto;
    }

    private MdItemSummaryDto toMdItemSummaryDto(MdItemSummary proto) {
        MdItemSummaryDto dto = new MdItemSummaryDto();
        dto.setId(proto.getId());
        dto.setName(proto.getName());
        dto.setThumbnailImageUrl(proto.getThumbnailImageUrl());
        dto.setPrice(proto.getPrice());
        dto.setSoldOut(proto.getIsSoldOut());
        return dto;
    }

    private MdItemDetailDto toMdItemDetailDto(MdItemDetail proto) {
        MdItemDetailDto dto = new MdItemDetailDto();
        dto.setId(proto.getId());
        dto.setName(proto.getName());
        dto.setThumbnailImageUrl(proto.getThumbnailImageUrl());
        dto.setDetailImageUrl(proto.getDetailImageUrl());
        dto.setPrice(proto.getPrice());
        dto.setSoldOut(proto.getIsSoldOut());
        dto.setProductDescription(proto.getProductDescription());
        dto.setDetailDescription(proto.getDetailDescription());
        dto.setOptionGroups(proto.getOptionGroupsList().stream()
                .map(this::toMdOptionGroupDto)
                .toList());
        return dto;
    }

    private MdOptionGroupDto toMdOptionGroupDto(MdOptionGroup proto) {
        MdOptionGroupDto dto = new MdOptionGroupDto();
        dto.setId(proto.getId());
        dto.setName(proto.getName());
        dto.setDisplayOrder(proto.getDisplayOrder());
        dto.setValues(proto.getValuesList().stream()
                .map(this::toMdOptionValueDto)
                .toList());
        return dto;
    }

    private MdOptionValueDto toMdOptionValueDto(MdOptionValue proto) {
        MdOptionValueDto dto = new MdOptionValueDto();
        dto.setId(proto.getId());
        dto.setValueName(proto.getValueName());
        dto.setExtraPrice(proto.getExtraPrice());
        dto.setSoldOut(proto.getIsSoldOut());
        dto.setDisplayOrder(proto.getDisplayOrder());
        return dto;
    }

    private NoticeSummaryDto toNoticeSummaryDto(NoticeSummary proto) {
        NoticeSummaryDto dto = new NoticeSummaryDto();
        dto.setId(proto.getId());
        dto.setTitle(proto.getTitle());
        dto.setPostedAt(proto.getPostedAt());
        dto.setPinned(proto.getIsPinned());
        return dto;
    }

    private NoticeDetailDto toNoticeDetailDto(NoticeDetail proto) {
        NoticeDetailDto dto = new NoticeDetailDto();
        dto.setId(proto.getId());
        dto.setTitle(proto.getTitle());
        dto.setContent(proto.getContent());
        dto.setPostedAt(proto.getPostedAt());
        dto.setPinned(proto.getIsPinned());
        return dto;
    }

    private PageInfoDto toPageInfoDto(com.playtab.contentservice.grpc.proto.v1.PageInfo proto) {
        PageInfoDto dto = new PageInfoDto();
        dto.setPage(proto.getPage());
        dto.setSize(proto.getSize());
        dto.setTotalElements(proto.getTotalElements());
        dto.setTotalPages(proto.getTotalPages());
        dto.setHasNext(proto.getHasNext());
        return dto;
    }

    private String resolveLocale(String locale) {
        return locale != null ? locale : DEFAULT_LOCALE;
    }
}
