package freedom.scmj.entity;

public class Operator
{
    private int opt;
    private Card    target;
    private boolean selfCard;
    
    public int getOpt()
    {
        return opt;
    }
    public void setOpt(int opt)
    {
        this.opt = opt;
    }
    public Card getTarget()
    {
        return target;
    }
    public void setTarget(Card target)
    {
        this.target = target;
    }
    
    public boolean isSelfCard()
    {
        return selfCard;
    }
    public void setSelfCard(boolean selfCard)
    {
        this.selfCard = selfCard;
    }
    @Override
    public String toString()
    {
	return "Operator [opt=" + opt + ", target=" + target + ", selfCard="
		+ selfCard + "]";
    }
}
