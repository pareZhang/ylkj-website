package org.springblade.modules.log.model;

import org.springblade.core.log.model.LogApi;

/**
 * @author zjm
 * @since 2021-05-17 9:43
 */
public class LogApiVo extends LogApi {
    private static final long serialVersionUID = 1L;
    private String strId;

    public LogApiVo() {
    }

    public String getStrId() {
        return this.strId;
    }

    public void setStrId(final String strId) {
        this.strId = strId;
    }

    @Override
    public String toString() {
        return "LogApiVo(strId=" + this.getStrId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LogApiVo)) {
            return false;
        } else {
            LogApiVo other = (LogApiVo)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$strId = this.getStrId();
                Object other$strId = other.getStrId();
                if (this$strId == null) {
                    if (other$strId != null) {
                        return false;
                    }
                } else if (!this$strId.equals(other$strId)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Override
    protected boolean canEqual(final Object other) {
        return other instanceof LogApiVo;
    }
    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = super.hashCode();
        Object $strId = this.getStrId();
        result = result * 59 + ($strId == null ? 43 : $strId.hashCode());
        return result;
    }

}
