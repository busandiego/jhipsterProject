package com.wmes.appserver.dto;

import com.wmes.appserver.domain.StateAdmin;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

import java.io.Serializable;
import java.util.Objects;

public class AdminUserCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StateAdmin
     */
    public static class StateAdminFilter extends Filter<StateAdmin> {

        public StateAdminFilter() {
        }

        public StateAdminFilter(StateAdminFilter filter) {
            super(filter);
        }

        @Override
        public StateAdminFilter copy() {
            return new StateAdminFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter adminLoginId;

    private StringFilter adminPassword;

    private StringFilter adminName;

    private StringFilter adminNick;

    private StringFilter adminNum;

    private StringFilter adminEmail;

    private ZonedDateTimeFilter adminCreateDt;

    private ZonedDateTimeFilter adminUpdateDt;

    private ZonedDateTimeFilter adminCurrentLoginDt;

    private StringFilter pushToken;

    private StateAdminFilter adminState;

    private LongFilter adminUserRoleId;

    private LongFilter adminUserAuthId;

    private LongFilter couponPublisherId;

    private LongFilter accommodationSalesId;

    private LongFilter boardId;

    private LongFilter boardDetailsId;

    private LongFilter inquiryAnswerId;

    private LongFilter termsId;

    private LongFilter hoEventId;

    private LongFilter hoNoticeId;

    private LongFilter affiliateCommissionSettingId;

    private LongFilter managerCommissionSettingUserId;

    private LongFilter pGCommissionSettingUserId;

    private LongFilter pGCommissionApplyUserId;

    private LongFilter investmentCommissionSettingUserId;

    private LongFilter investmentCommissionApplyUserId;

    private LongFilter mileageSettingId;

    private LongFilter inflowPathId;

    private LongFilter categoryId;

    private LongFilter bookmarkId;

    private LongFilter timeApplyManagerId;

    private LongFilter settingUserId;

    private LongFilter invitePublishHistoriesId;

    private LongFilter affiliateId;

    private LongFilter accommodationId;

    private LongFilter faqBoardId;

    public AdminUserCriteria(){
    }

    public AdminUserCriteria(AdminUserCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.adminLoginId = other.adminLoginId == null ? null : other.adminLoginId.copy();
        this.adminPassword = other.adminPassword == null ? null : other.adminPassword.copy();
        this.adminName = other.adminName == null ? null : other.adminName.copy();
        this.adminNick = other.adminNick == null ? null : other.adminNick.copy();
        this.adminNum = other.adminNum == null ? null : other.adminNum.copy();
        this.adminEmail = other.adminEmail == null ? null : other.adminEmail.copy();
        this.adminCreateDt = other.adminCreateDt == null ? null : other.adminCreateDt.copy();
        this.adminUpdateDt = other.adminUpdateDt == null ? null : other.adminUpdateDt.copy();
        this.adminCurrentLoginDt = other.adminCurrentLoginDt == null ? null : other.adminCurrentLoginDt.copy();
        this.pushToken = other.pushToken == null ? null : other.pushToken.copy();
        this.adminState = other.adminState == null ? null : other.adminState.copy();
        this.adminUserRoleId = other.adminUserRoleId == null ? null : other.adminUserRoleId.copy();
        this.adminUserAuthId = other.adminUserAuthId == null ? null : other.adminUserAuthId.copy();
        this.couponPublisherId = other.couponPublisherId == null ? null : other.couponPublisherId.copy();
        this.accommodationSalesId = other.accommodationSalesId == null ? null : other.accommodationSalesId.copy();
        this.boardId = other.boardId == null ? null : other.boardId.copy();
        this.boardDetailsId = other.boardDetailsId == null ? null : other.boardDetailsId.copy();
        this.inquiryAnswerId = other.inquiryAnswerId == null ? null : other.inquiryAnswerId.copy();
        this.termsId = other.termsId == null ? null : other.termsId.copy();
        this.hoEventId = other.hoEventId == null ? null : other.hoEventId.copy();
        this.hoNoticeId = other.hoNoticeId == null ? null : other.hoNoticeId.copy();
        this.affiliateCommissionSettingId = other.affiliateCommissionSettingId == null ? null : other.affiliateCommissionSettingId.copy();
        this.managerCommissionSettingUserId = other.managerCommissionSettingUserId == null ? null : other.managerCommissionSettingUserId.copy();
        this.pGCommissionSettingUserId = other.pGCommissionSettingUserId == null ? null : other.pGCommissionSettingUserId.copy();
        this.pGCommissionApplyUserId = other.pGCommissionApplyUserId == null ? null : other.pGCommissionApplyUserId.copy();
        this.investmentCommissionSettingUserId = other.investmentCommissionSettingUserId == null ? null : other.investmentCommissionSettingUserId.copy();
        this.investmentCommissionApplyUserId = other.investmentCommissionApplyUserId == null ? null : other.investmentCommissionApplyUserId.copy();
        this.mileageSettingId = other.mileageSettingId == null ? null : other.mileageSettingId.copy();
        this.inflowPathId = other.inflowPathId == null ? null : other.inflowPathId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.bookmarkId = other.bookmarkId == null ? null : other.bookmarkId.copy();
        this.timeApplyManagerId = other.timeApplyManagerId == null ? null : other.timeApplyManagerId.copy();
        this.settingUserId = other.settingUserId == null ? null : other.settingUserId.copy();
        this.invitePublishHistoriesId = other.invitePublishHistoriesId == null ? null : other.invitePublishHistoriesId.copy();
        this.affiliateId = other.affiliateId == null ? null : other.affiliateId.copy();
        this.accommodationId = other.accommodationId == null ? null : other.accommodationId.copy();
        this.faqBoardId = other.faqBoardId == null ? null : other.faqBoardId.copy();
    }

    @Override
    public AdminUserCriteria copy() {
        return new AdminUserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAdminLoginId() {
        return adminLoginId;
    }

    public void setAdminLoginId(StringFilter adminLoginId) {
        this.adminLoginId = adminLoginId;
    }

    public StringFilter getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(StringFilter adminPassword) {
        this.adminPassword = adminPassword;
    }

    public StringFilter getAdminName() {
        return adminName;
    }

    public void setAdminName(StringFilter adminName) {
        this.adminName = adminName;
    }

    public StringFilter getAdminNick() {
        return adminNick;
    }

    public void setAdminNick(StringFilter adminNick) {
        this.adminNick = adminNick;
    }

    public StringFilter getAdminNum() {
        return adminNum;
    }

    public void setAdminNum(StringFilter adminNum) {
        this.adminNum = adminNum;
    }

    public StringFilter getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(StringFilter adminEmail) {
        this.adminEmail = adminEmail;
    }

    public ZonedDateTimeFilter getAdminCreateDt() {
        return adminCreateDt;
    }

    public void setAdminCreateDt(ZonedDateTimeFilter adminCreateDt) {
        this.adminCreateDt = adminCreateDt;
    }

    public ZonedDateTimeFilter getAdminUpdateDt() {
        return adminUpdateDt;
    }

    public ZonedDateTimeFilter getAdminCurrentLoginDt() {
        return adminCurrentLoginDt;
    }

    public void setAdminCurrentLoginDt(ZonedDateTimeFilter adminCurrentLoginDt) {
        this.adminCurrentLoginDt = adminCurrentLoginDt;
    }

    public void setAdminUpdateDt(ZonedDateTimeFilter adminUpdateDt) {
        this.adminUpdateDt = adminUpdateDt;
    }

    public StringFilter getPushToken() {
        return pushToken;
    }

    public void setPushToken(StringFilter pushToken) {
        this.pushToken = pushToken;
    }

    public StateAdminFilter getAdminState() {
        return adminState;
    }

    public void setAdminState(StateAdminFilter adminState) {
        this.adminState = adminState;
    }

    public LongFilter getAdminUserRoleId() {
        return adminUserRoleId;
    }

    public void setAdminUserRoleId(LongFilter adminUserRoleId) {
        this.adminUserRoleId = adminUserRoleId;
    }

    public LongFilter getAdminUserAuthId() {
        return adminUserAuthId;
    }

    public void setAdminUserAuthId(LongFilter adminUserAuthId) {
        this.adminUserAuthId = adminUserAuthId;
    }

    public LongFilter getCouponPublisherId() {
        return couponPublisherId;
    }

    public void setCouponPublisherId(LongFilter couponPublisherId) {
        this.couponPublisherId = couponPublisherId;
    }

    public LongFilter getAccommodationSalesId() {
        return accommodationSalesId;
    }

    public void setAccommodationSalesId(LongFilter accommodationSalesId) {
        this.accommodationSalesId = accommodationSalesId;
    }

    public LongFilter getBoardId() {
        return boardId;
    }

    public void setBoardId(LongFilter boardId) {
        this.boardId = boardId;
    }

    public LongFilter getBoardDetailsId() {
        return boardDetailsId;
    }

    public void setBoardDetailsId(LongFilter boardDetailsId) {
        this.boardDetailsId = boardDetailsId;
    }

    public LongFilter getInquiryAnswerId() {
        return inquiryAnswerId;
    }

    public void setInquiryAnswerId(LongFilter inquiryAnswerId) {
        this.inquiryAnswerId = inquiryAnswerId;
    }

    public LongFilter getTermsId() {
        return termsId;
    }

    public void setTermsId(LongFilter termsId) {
        this.termsId = termsId;
    }

    public LongFilter getHoEventId() {
        return hoEventId;
    }

    public void setHoEventId(LongFilter hoEventId) {
        this.hoEventId = hoEventId;
    }

    public LongFilter getHoNoticeId() {
        return hoNoticeId;
    }

    public void setHoNoticeId(LongFilter hoNoticeId) {
        this.hoNoticeId = hoNoticeId;
    }

    public LongFilter getAffiliateCommissionSettingId() {
        return affiliateCommissionSettingId;
    }

    public void setAffiliateCommissionSettingId(LongFilter affiliateCommissionSettingId) {
        this.affiliateCommissionSettingId = affiliateCommissionSettingId;
    }

    public LongFilter getManagerCommissionSettingUserId() {
        return managerCommissionSettingUserId;
    }

    public void setManagerCommissionSettingUserId(LongFilter managerCommissionSettingUserId) {
        this.managerCommissionSettingUserId = managerCommissionSettingUserId;
    }

    public LongFilter getPGCommissionSettingUserId() {
        return pGCommissionSettingUserId;
    }

    public void setPGCommissionSettingUserId(LongFilter pGCommissionSettingUserId) {
        this.pGCommissionSettingUserId = pGCommissionSettingUserId;
    }

    public LongFilter getPGCommissionApplyUserId() {
        return pGCommissionApplyUserId;
    }

    public void setPGCommissionApplyUserId(LongFilter pGCommissionApplyUserId) {
        this.pGCommissionApplyUserId = pGCommissionApplyUserId;
    }

    public LongFilter getInvestmentCommissionSettingUserId() {
        return investmentCommissionSettingUserId;
    }

    public void setInvestmentCommissionSettingUserId(LongFilter investmentCommissionSettingUserId) {
        this.investmentCommissionSettingUserId = investmentCommissionSettingUserId;
    }

    public LongFilter getInvestmentCommissionApplyUserId() {
        return investmentCommissionApplyUserId;
    }

    public void setInvestmentCommissionApplyUserId(LongFilter investmentCommissionApplyUserId) {
        this.investmentCommissionApplyUserId = investmentCommissionApplyUserId;
    }

    public LongFilter getMileageSettingId() {
        return mileageSettingId;
    }

    public void setMileageSettingId(LongFilter mileageSettingId) {
        this.mileageSettingId = mileageSettingId;
    }

    public LongFilter getInflowPathId() {
        return inflowPathId;
    }

    public void setInflowPathId(LongFilter inflowPathId) {
        this.inflowPathId = inflowPathId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(LongFilter bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public LongFilter getTimeApplyManagerId() {
        return timeApplyManagerId;
    }

    public void setTimeApplyManagerId(LongFilter timeApplyManagerId) {
        this.timeApplyManagerId = timeApplyManagerId;
    }

    public LongFilter getSettingUserId() {
        return settingUserId;
    }

    public void setSettingUserId(LongFilter settingUserId) {
        this.settingUserId = settingUserId;
    }

    public LongFilter getInvitePublishHistoriesId() {
        return invitePublishHistoriesId;
    }

    public void setInvitePublishHistoriesId(LongFilter invitePublishHistoriesId) {
        this.invitePublishHistoriesId = invitePublishHistoriesId;
    }

    public LongFilter getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(LongFilter affiliateId) {
        this.affiliateId = affiliateId;
    }

    public LongFilter getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(LongFilter accommodationId) {
        this.accommodationId = accommodationId;
    }

    public LongFilter getFaqBoardId() {
        return faqBoardId;
    }

    public void setFaqBoardId(LongFilter faqBoardId) {
        this.faqBoardId = faqBoardId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdminUserCriteria that = (AdminUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(adminLoginId, that.adminLoginId) &&
                Objects.equals(adminPassword, that.adminPassword) &&
                Objects.equals(adminName, that.adminName) &&
                Objects.equals(adminNick, that.adminNick) &&
                Objects.equals(adminNum, that.adminNum) &&
                Objects.equals(adminEmail, that.adminEmail) &&
                Objects.equals(adminCreateDt, that.adminCreateDt) &&
                Objects.equals(adminUpdateDt, that.adminUpdateDt) &&
                Objects.equals(adminCurrentLoginDt, that.adminCurrentLoginDt) &&
                Objects.equals(pushToken, that.pushToken) &&
                Objects.equals(adminState, that.adminState) &&
                Objects.equals(adminUserRoleId, that.adminUserRoleId) &&
                Objects.equals(adminUserAuthId, that.adminUserAuthId) &&
                Objects.equals(couponPublisherId, that.couponPublisherId) &&
                Objects.equals(accommodationSalesId, that.accommodationSalesId) &&
                Objects.equals(boardId, that.boardId) &&
                Objects.equals(boardDetailsId, that.boardDetailsId) &&
                Objects.equals(inquiryAnswerId, that.inquiryAnswerId) &&
                Objects.equals(termsId, that.termsId) &&
                Objects.equals(hoEventId, that.hoEventId) &&
                Objects.equals(hoNoticeId, that.hoNoticeId) &&
                Objects.equals(affiliateCommissionSettingId, that.affiliateCommissionSettingId) &&
                Objects.equals(managerCommissionSettingUserId, that.managerCommissionSettingUserId) &&
                Objects.equals(pGCommissionSettingUserId, that.pGCommissionSettingUserId) &&
                Objects.equals(pGCommissionApplyUserId, that.pGCommissionApplyUserId) &&
                Objects.equals(investmentCommissionSettingUserId, that.investmentCommissionSettingUserId) &&
                Objects.equals(investmentCommissionApplyUserId, that.investmentCommissionApplyUserId) &&
                Objects.equals(mileageSettingId, that.mileageSettingId) &&
                Objects.equals(inflowPathId, that.inflowPathId) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(bookmarkId, that.bookmarkId) &&
                Objects.equals(timeApplyManagerId, that.timeApplyManagerId) &&
                Objects.equals(settingUserId, that.settingUserId) &&
                Objects.equals(invitePublishHistoriesId, that.invitePublishHistoriesId) &&
                Objects.equals(affiliateId, that.affiliateId) &&
                Objects.equals(accommodationId, that.accommodationId) &&
                Objects.equals(faqBoardId, that.faqBoardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            adminLoginId,
            adminPassword,
            adminName,
            adminNick,
            adminNum,
            adminEmail,
            adminCreateDt,
            adminUpdateDt,
            adminCurrentLoginDt,
            pushToken,
            adminState,
            adminUserRoleId,
            adminUserAuthId,
            couponPublisherId,
            accommodationSalesId,
            boardId,
            boardDetailsId,
            inquiryAnswerId,
            termsId,
            hoEventId,
            hoNoticeId,
            affiliateCommissionSettingId,
            managerCommissionSettingUserId,
            pGCommissionSettingUserId,
            pGCommissionApplyUserId,
            investmentCommissionSettingUserId,
            investmentCommissionApplyUserId,
            mileageSettingId,
            inflowPathId,
            categoryId,
            bookmarkId,
            timeApplyManagerId,
            settingUserId,
            invitePublishHistoriesId,
            affiliateId,
            accommodationId,
            faqBoardId
        );
    }

    @Override
    public String toString() {
        return "AdminUserCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (adminLoginId != null ? "adminLoginId=" + adminLoginId + ", " : "") +
            (adminPassword != null ? "adminPassword=" + adminPassword + ", " : "") +
            (adminName != null ? "adminName=" + adminName + ", " : "") +
            (adminNick != null ? "adminNick=" + adminNick + ", " : "") +
            (adminNum != null ? "adminNum=" + adminNum + ", " : "") +
            (adminEmail != null ? "adminEmail=" + adminEmail + ", " : "") +
            (adminCreateDt != null ? "adminCreateDt=" + adminCreateDt + ", " : "") +
            (adminUpdateDt != null ? "adminUpdateDt=" + adminUpdateDt + ", " : "") +
            (adminCurrentLoginDt != null ? "adminCurrentLoginDt=" + adminCurrentLoginDt + ", " : "") +
            (pushToken != null ? "pushToken=" + pushToken + ", " : "") +
            (adminState != null ? "adminState=" + adminState + ", " : "") +
            (adminUserRoleId != null ? "adminUserRoleId=" + adminUserRoleId + ", " : "") +
            (adminUserAuthId != null ? "adminUserAuthId=" + adminUserAuthId + ", " : "") +
            (couponPublisherId != null ? "couponPublisherId=" + couponPublisherId + ", " : "") +
            (accommodationSalesId != null ? "accommodationSalesId=" + accommodationSalesId + ", " : "") +
            (boardId != null ? "boardId=" + boardId + ", " : "") +
            (boardDetailsId != null ? "boardDetailsId=" + boardDetailsId + ", " : "") +
            (inquiryAnswerId != null ? "inquiryAnswerId=" + inquiryAnswerId + ", " : "") +
            (termsId != null ? "termsId=" + termsId + ", " : "") +
            (hoEventId != null ? "hoEventId=" + hoEventId + ", " : "") +
            (hoNoticeId != null ? "hoNoticeId=" + hoNoticeId + ", " : "") +
            (affiliateCommissionSettingId != null ? "affiliateCommissionSettingId=" + affiliateCommissionSettingId + ", " : "") +
            (managerCommissionSettingUserId != null ? "managerCommissionSettingUserId=" + managerCommissionSettingUserId + ", " : "") +
            (pGCommissionSettingUserId != null ? "pGCommissionSettingUserId=" + pGCommissionSettingUserId + ", " : "") +
            (pGCommissionApplyUserId != null ? "pGCommissionApplyUserId=" + pGCommissionApplyUserId + ", " : "") +
            (investmentCommissionSettingUserId != null ? "investmentCommissionSettingUserId=" + investmentCommissionSettingUserId + ", " : "") +
            (investmentCommissionApplyUserId != null ? "investmentCommissionApplyUserId=" + investmentCommissionApplyUserId + ", " : "") +
            (mileageSettingId != null ? "mileageSettingId=" + mileageSettingId + ", " : "") +
            (inflowPathId != null ? "inflowPathId=" + inflowPathId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (bookmarkId != null ? "bookmarkId=" + bookmarkId + ", " : "") +
            (timeApplyManagerId != null ? "timeApplyManagerId=" + timeApplyManagerId + ", " : "") +
            (settingUserId != null ? "settingUserId=" + settingUserId + ", " : "") +
            (invitePublishHistoriesId != null ? "invitePublishHistoriesId=" + invitePublishHistoriesId + ", " : "") +
            (affiliateId != null ? "affiliateId=" + affiliateId + ", " : "") +
            (accommodationId != null ? "accommodationId=" + accommodationId + ", " : "") +
            (faqBoardId != null ? "faqBoardId=" + faqBoardId + ", " : "") +
            "}";
    }

}
