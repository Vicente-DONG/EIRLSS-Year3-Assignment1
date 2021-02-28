
package Objects.VisitorPattern;

public interface Visitable {
    public String acceptBooleanVisitor(Visitor v, boolean b);
}
