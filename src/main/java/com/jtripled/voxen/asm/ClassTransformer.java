package com.jtripled.voxen.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 *
 * @author jtripled
 */
public abstract class ClassTransformer implements IClassTransformer
{
    private String className;
    private boolean obfuscated;
    
    public ClassTransformer()
    {
        this.className = null;
        this.obfuscated = false;
    }
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        for (String className : getClasses())
        {
            if (transformedName.equals(className))
            {
                System.out.println("Applying ASM transformations to '" + className + "'.");
                this.obfuscated = !transformedName.equals(name);
                this.className = className;
                ClassNode node = new ClassNode();
                ClassReader reader = new ClassReader(bytes);
                reader.accept(node, 0);
                bytes = onTransform(className, bytes, node, obfuscated);
                System.out.println("Successfully applied ASM transformations to '" + className + "'.");
                this.obfuscated = false;
                this.className = null;
                return bytes;
            }
        }
        return bytes;
    }
    
    public byte[] onTransform(String className, byte[] bytes, ClassNode node, boolean obfuscated)
    {
        return bytes;
    }
    
    public abstract String[] getClasses();
    
    public String createMethodDescription(String returnType, String... argTypes)
    {
        return createMethodDescriptor(obfuscated, returnType, argTypes);
    }
    
    public boolean isClass(String className)
    {
        return this.className.equals(className);
    }
    
    public boolean isMethod(MethodNode node, String[] names, String desc)
    {
        return methodEquals(node, names, desc);
    }
    
    public static boolean methodEquals(MethodNode methodNode, String[] names, String desc)
    {
        boolean nameMatches = false;
        
        for (String name : names)
        {
            if (methodNode.name.equals(name))
            {
                nameMatches = true;
                break;
            }
        }
        
        return nameMatches && methodNode.desc.equals(desc);
    }
    
    public static String createMethodDescriptor(boolean obfuscated, String returnType, String... types)
    {
        String result = "(";
        
        for (String type : types)
        {
            if (type.length() == 1) result += type;
            else
            {
                result += "L" + (obfuscated ? FMLDeobfuscatingRemapper.INSTANCE.unmap(type) : type) + ";";
            }
        }
        
        if (returnType.length() > 1)
        {
            returnType = "L" + unmapType(obfuscated, returnType) + ";";
        }
        
        result += ")" + returnType;
        
        return result;
    }
    
    public static String unmapType(boolean obfuscated, String type)
    {
        return obfuscated ? FMLDeobfuscatingRemapper.INSTANCE.unmap(type) : type;
    }
}
