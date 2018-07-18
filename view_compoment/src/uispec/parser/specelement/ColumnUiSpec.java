package uispec.parser.specelement;

public class ColumnUiSpec extends ContainerUiSpec {

    @Override
    public String getElementTypeName() {
        return "container";
    }

    @Override
    public String getCssClass() {
        String clazz = super.getCssClass();
        if (clazz == null || clazz.isEmpty()) {
            return "container--column";
        }
        return "container--column " + clazz;
    }

}
