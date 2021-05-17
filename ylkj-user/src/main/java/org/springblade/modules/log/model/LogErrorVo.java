package org.springblade.modules.log.model;

import org.springblade.core.log.model.LogError;

/**
 * @author zjm
 * @since 2021-05-17 9:56
 */
public class LogErrorVo extends LogError {
    private static final long serialVersionUID = 1L;
    private String strId;

    public LogErrorVo() {
    }

    public String getStrId() {
        return this.strId;
    }

    public void setStrId(final String strId) {
        this.strId = strId;
    }

    @Override
    public String toString() {
        return "LogErrorVo(strId=" + this.getStrId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LogErrorVo)) {
            return false;
        } else {
            LogErrorVo other = (LogErrorVo)o;
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
        return other instanceof LogErrorVo;
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
