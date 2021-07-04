package hu.indicium.dev.ledenadministratie.domain.model.user.member.membership;

import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Membership {

    @EmbeddedId
    private MembershipId membershipId;

    private Date startDate;

    private Date endDate;

    @ManyToOne(optional = false)
    private Member member;

    private PaymentId paymentId;

    private MembershipStatus status;

    public Membership(MembershipId membershipId, Date startDate, Date endDate, Member member) {
        this.membershipId = membershipId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = member;
        this.status = MembershipStatus.PENDING;
    }

    public int getStartYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.startDate);
        return calendar.get(Calendar.YEAR);
    }

    public int getEndYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.endDate);
        return calendar.get(Calendar.YEAR);
    }

    public void assignPayment(PaymentId paymentId) {
        this.paymentId = paymentId;
    }

    public void activate() {
        this.status = MembershipStatus.ACTIVE;
    }

    public void cancel() {
        this.status = MembershipStatus.CANCELLED;
    }

    public boolean isActive() {
        return this.endDate.after(new Date()) && status == MembershipStatus.ACTIVE;
    }
}
